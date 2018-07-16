#!/bin/sh

# OE Build Enviroment Setup Script
#
# This script was created with reference to the following scripts
#   From git://git.openembedded.org/openembedded-core
#       * oe-init-build-env
#       * scripts/*
#   From git://gitorious.org/angstrom/angstrom-setup-scripts.git
#       * oebb.sh
#
#
# Copyright (C) 2012 Texas Instruments
#
# This program is free software; you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation; either version 2 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program; if not, write to the Free Software
# Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
###############################################################################

# TODO:
#   Enhance this script to allow for reconfiguring repositories.  Currently if
#   a repository is already seen we print an error and continue on.

####################
# Global variables #
####################
OECORELAYERCONF="" # sample configuration file used for bblayers.conf
OECORELAYERCONFPATH="" # stores the path of the OECORELAYERCONFPATH variable
OECORELOCALCONF="" # sample configuration file used for local.conf
OECORELOCALCONFPATH="" # stores the path of the OECORELOCALCONFPATH variable
outputfile="" # file to save output to if -o is used
inputfile="configs/arago-rocko-config.txt" # file containing initial layers if -f is used
interactive="n" # flag for interactive mode.  Default is n
layers="" # variable holding the layers to add to bblayers.conf
output="" # variable holding the output to write to outputfile
scriptdir=`pwd` # directory of this calling script
oebase=`pwd` # variable to hold base directory
sourcedir="" # directory where repos will be cloned
builddir="" # directory for builds
confdir="" # directory for build configuration files
dldir="" # setting for DL_DIR if -d option is used


#####################
# Parsing variables #
#####################
name="" # variable holding the name of the repo being cloned
uri="" # variable holding the URI of the repo being cloned
branch="" #variable holding the branch to checkout
commit="" #variable holding the commit or tag to use
repo_layers="" # variable to hold the layers for a repo during parsing

####################
# Helper Functions #
####################
usage() {
cat <<EOM


Usage:
    $0 [-i] [-f inputfile] [-o outputfile] [-d dldir] [-b basedir] [-h]

Parameters:
    -i: Interactive mode.  This mode will query the user to input information
        for each repository and which layers to configure for that repository.
    -f: Specifies the input file to be parsed which represents the repositories
        to be configured.  If this option is used in conjunction with the -i
        option then the setting in the input file are used first and then the
        user is prompted for additional repositories to configure.
    -o: Specifies the output file to save the repository configurations to.
        This allows the user to share the settings they enter with interactive
        mode with others.
    -d: This optional directory points to where the downloads should be saved
        by setting the DL_DIR setting in the configuration files.
    -b: This optional directory will be the location of the base directory
        where the build will be configured.  The default is the current
        directory.
    -h: This message

Input Files:
    Input files are expected to have the following format
        name,repo uri,branch,commit[,layers=layer1:layer2:...:layerN]

    The first 4 values are required, whereas the layers= value is optional.
    By default all layer.conf files found in a repository will be selected
    unless the layers= option is set to limit the layers being used.  The
    settings for the layers option should be the path from the base of the
    repository to the directory containing a conf directory with the layer.conf
    file.  For example, when configuring openembedded-core the following can be
    used:

    openembedded-core,git://github.com/openembedded/oe-core.git,master,HEAD

    This would select both the meta and meta-skeleton layers.

    or, to limit to only the meta layer you could use the syntax

    openembedded-core,git://github.com/openembedded/oe-core.git,master,HEAD,layers=meta

    or, to explicitly set the meta and meta-skeleton layers use

    openembedded-core,git://github.com/openembedded/oe-core.git,master,HEAD,layers=meta:meta-skeleton

    It is also possible to specify the sample conf files for bblayers.conf
    and local.conf using settings like:
        OECORELAYERCONF=relative/path/to/bblayers.conf.sample
        OECORELOCALCONF=relative/path/to/local.conf.sample

EOM
exit 1
}


check_input() {
    # Check that at least -i or -f was used
    if [ "$interactive" = "n" -a "x$inputfile" = "x" ]
    then
        echo "ERROR: You must either use this script with the -i or -f options"
        usage
    fi

    # If an input file was given make sure it exists
    if [ ! -f $inputfile ]
    then
        echo "ERROR: the file \"$inputfile\" given for inputfile does not exist"
        usage 
    fi

    # If directories do not exist then create them
    for f in sourcedir builddir confdir
    do
        eval t="$"$f
        if [ ! -d $t ]
        then
            mkdir -p $t
        fi
    done

}


# Input is a line of the form OECORE.*=value
parse_oecore_line() {
    var=`echo $1 | cut -d= -f1`
    val=`echo $1 | cut -d= -f2`
    eval $var=$val
}


# Input is a repository description line as detailed in the usage section
# There is a second optional input that can be passed to prefix the variable
# names.  This is useful in the case that you want to parse two lines
# for comparison at the same time.
#   In this way if prefix is set to "temp_" then the name of the repo
#   will be saved into the variable temp_name instead of name
# NOTE: If the format of the input file changes this function will need
#       to change as well.
parse_repo_line() {
    # Set the variable prefix if there is any
    prefix=$2

    # split the line on the comma separators
    # use the prefix if it was set.
    eval $prefix"name"=`echo $1 | cut -d, -f1`
    eval $prefix"uri"=`echo $1 | cut -d, -f2`
    eval $prefix"branch"=`echo $1 | cut -d, -f3`
    eval $prefix"commit"=`echo $1 | cut -d, -f4`
    parsed_layers=`echo $1 | cut -d, -f5-`

    # Blank the temp variable
    temp_layers=""

    # If layers= was used then set the layers variable
    if [ "x$parsed_layers" != "x" ]
    then
        temp=`echo $parsed_layers | cut -d= -f2`
        # temporarily reset the IFS value to : to split the layers
        for x in `IFS=":";echo $temp`
        do
            # Add the $name value to each layer so that we have consistency
            # with how the layers are represented between the saved value
            # and the dynamically parsed values
            temp_layers="$temp_layers""$name/$x "
        done
    fi

    # Assign the layers.  If the temp_layers is empty then set the layers
    # to all and we will fill in the actual layers in the later steps.
    if [ "x$temp_layers" = "x" ]
    then
        eval $prefix"repo_layers"="all"
    else 
        eval $prefix"repo_layers"='$temp_layers'
    fi
}


parse_input_file() {
    while read line
    do
        # clean the parsing variables for each run
        name=""
        uri=""
        branch=""
        commit=""
        repo_layers=""

        # Skip empty lines
        if [ "x$line" = "x" ]
        then
            continue
        fi

        # Skip comment lines
        echo $line | grep -e "^#" > /dev/null
        if [ "$?" = "0" ]
        then
            continue
        fi

        # If the line starts with OECORE then parse the OECORE setting
        echo $line | grep -e "^OECORE.*=" > /dev/null
        if [ "$?" = "0" ]
        then
            parse_oecore_line $line
            output="$output""$line\n"
            continue
        fi

        # Since the line is not a comment or an OECORE setting let's assume
        # it is a repository information line and parse it
        parse_repo_line $line

        configure_repo

        # if the return from configure repo was non-zero then do not save
        # the output
        if [ "$?" != "0" ]
        then
            continue
        fi

        # Create the repository line corresponding to the selections given.
        # In the case that no layers= option was passed then this will
        # create the layers= option corresponding to all layers being selected.
        repo_line=`build_repo_line`

        # Save the line in the output variable for if we create an output file
        output="$output""$repo_line\n"

        save_layers

    done < $inputfile
}


configure_repo() {
    if [ "x$name" = "x" ]
    then
        get_repo_name
    fi

    # Check if the repo with $name was already seen.  Use the , at the end
    # of the grep to avoid matching similar named repos.
    temp=`printf "$output\n" | grep -e "^$name,"`

    if [ "x$temp" != "x" ]
    then
        echo "This repository ($name) has already been configured with the following values:"
        printf "\t$temp\n"
        echo "Skipping configuring duplicate repository"
        return 1
    fi
  
    if [ "x$uri" = "x" ]
    then
        get_repo_uri
    fi

    echo ""
    echo ""
    echo "cloning repo $name"
    echo ""

    clone_repo

    if [ "x$branch" = "x" ]
    then
        get_repo_branch
    fi

    checkout_branch

    if [ "x$commit" = "x" ]
    then
        get_repo_commit
    fi

    checkout_commit

    if [ "x$repo_layers" = "xall" ]
    then
        # Call select layers with the all option to select all layers
        select_layers "all"
    elif [ "x$repo_layers" = "x" ]
    then
        select_layers
    fi

    verify_layers
}

clone_repo() {
    # check if the repo already exists.  if so then fetch the latest updates,
    # else clone it
    if [ -d $sourcedir/$name ]
    then
        cd $sourcedir/$name
        git fetch --all
    else
        git clone $uri $sourcedir/$name
        if [ "$?" != "0" ]
        then
            echo "ERROR: Could not clone repository at $uri"
            exit 1
        fi
    fi
}

get_repo_branch() {
    found=0
    branches=""

    while [ "$found" = "0" ]
    do
        cd $sourcedir/$name

        # Get a unique list of branches for the user to chose from
        # Also delete the origin/HEAD line that the -r option returns
        t_branches=`git branch -r | sed '/origin\/HEAD/d'`
        for b in $t_branches
        do
            branches="$branches"`echo $b | sed 's:.*origin/::g'`"\n"
        done
        branches=`printf "$branches\n" | sort | uniq`

cat << EOM


################################################################################
The $name repository has the following branches available:

$branches

What branch would you like to checkout for the $name repository? 
EOM
        read input

        # check that the branch is in the list of branches
        # NOTE: using a for loop here because I want exact matches.
        #       i.e if I used grep then master would also match master-next
        for b in $branches
        do
            if [ "$b" = "$input" ]
            then
                found=1
            fi
        done

        if [ "$found" != "1" ]
        then
            echo "Invalid branch ($input) selected.  Please try again"
        fi
    done
    branch=$input
}

checkout_branch() {
    cd $sourcedir/$name

    # Check if a local branch already exists to track the remote branch.
    # If not then create a tracking branch and checkout the branch
    # else just checkout the existing branch
    git branch | grep $branch > /dev/null
    if [ "$?" != "0" ]
    then
        git checkout origin/$branch -b $branch --track
    else
        git checkout $branch
    fi

    # Now that we are on the proper branch merge the remote branch changes if
    # any.  In the case of a clean checkout this should be already up to date,
    # but for an existing checkout this should be the changes that were
    # fetched earlier.
    git merge origin/$branch
}

checkout_commit() {
    cd $sourcedir/$name
    if [ "x$commit" != "xHEAD" ]
    then
        git checkout $commit
    fi
}

get_repo_commit() {
  # prompt for what commit to use with HEAD as default
    cd $sourcedir/$name
cat << EOM


################################################################################
The $name repository has the following tags available:

EOM
    tags=`git tag`
    if [ "x$tags" = "x" ]
    then
        printf "\tNo tags found\n"
    else
        # Format the tags nicely
        for t in $tags
        do
            printf "\t* $t\n"
        done
    fi
cat << EOM

You can either select one of these tags or specify your own commit SHA sum, or
press ENTER to use the HEAD of the current branch.
EOM

    read input

    if [ "x$input" = "x" ]
    then
        commit="HEAD"
    fi
}

verify_layers() {
    cd $sourcedir
    for l in $repo_layers
    do
        if [ ! -f $sourcedir/$l/conf/layer.conf ]
        then
            echo "ERROR: the $l layer in the $name repository could not be"
            echo "       found.  Bailing out."
            exit 1
        fi
    done
}


save_layers() {
    # Add the repo layers to the layers list
    for l in $repo_layers
    do
        layers="$layers""$sourcedir/$l "
    done
}


select_layers() {
    # Find the layers available in the repository and let the user select the
    # ones they want to include..  If all is passed then take all of them.
    arg1="$1"
    input=""

    #check if any layers exist
    #If so prompt for which layers to configure
    #If there is only one then just configure that layer and don't prompt

    cd $sourcedir
    # Get a count of how many layers there are
    count=`find $name -name "layer.conf" | grep -c layer.conf`

    case $count in
        0 )
            repo_layers=""
            return
            ;;
        1 )
            # This is the case where the repo contains just a conf/layer.conf
            # and is a single layer.
            repo_layers="$name"
            return
            ;;
    esac

    t_layers=`find $name -name "layer.conf" | sed 's:\/conf\/layer.conf::'`

    if [ "x$arg1" != "xall" ]
    then
        echo "arg1 = $arg1"
        # Prompt for which layers to configure
cat << EOM


################################################################################
The $name repository contains the following layers:

EOM

    for l in $t_layers
    do
        printf "\t"`echo $l | sed "s:${name}\/::"`"\n"
    done

cat << EOM

Please provide the list of layers you wish to use as a space separated list,
or press enter to use all of the layers.
EOM

        read input
    fi

    if [ "x$input" = "x" ]
    then
        repo_layers=$t_layers
    else
        repo_layers=$input
    fi
}

get_repo_name() {
cat << EOM


################################################################################
What is the name of the repository you want to configure?
EOM

    read name
}


get_repo_uri() {
cat << EOM


################################################################################
What is the git clone uri of the $name repository?
EOM

    read uri
}


get_oecorelayerconf() {
    # Check if the variable is already set.
    if [ "x$OECORELAYERCONF" != "x" ]
    then
        OECORELAYERCONFPATH=$scriptdir/$OECORELAYERCONF

        if [ ! -e $OECORELAYERCONFPATH ]
        then
            echo "ERROR: Could not find the specified layer conf file $OECORELAYERCONFPATH"
        fi

        return
    fi

    cd $sourcedir
    confs=`find . -name "bblayers.conf.sample"`

    done="n"

    while [ "$done" != "y" ]
    do

cat << EOM


################################################################################
The following sample bblayers.conf files have been found:

EOM

        for f in $confs
        do
            printf "\t$f\n"
        done

cat << EOM
Please select one of the above sample files to use as a template for
configuring your build environment.
EOM

        read input

        if [ -e $input ]
        then
            done="y"
            OECORELAYERCONF=$input
            OECORELAYERCONFPATH=$sourcedir/$OECORELAYERCONF
        else
            echo "ERROR: Could not find the specified layer conf file $input"
        fi
    done
}


get_oecorelocalconf() {
    # Check if the variable is already set.
    if [ "x$OECORELOCALCONF" != "x" ]
    then
        OECORELOCALCONFPATH=$scriptdir/$OECORELOCALCONF

        if [ ! -e $OECORELOCALCONFPATH ]
        then
            echo "ERROR: Could not find the specified local conf file $OECORELOCALCONFPATH"
            exit 1
        fi

        return
    fi

    cd $sourcedir
    confs=`find . -name "local.conf.sample"`

    done="n"

    while [ "$done" != "y" ]
    do

cat << EOM


################################################################################
The following sample local.conf files have been found:

EOM

        for f in $confs
        do
            printf "\t$f\n"
        done

cat << EOM
Please select one of the above sample files to use as a template for
configuring your build environment.
EOM

        read input

        if [ -e $input ]
        then
            done="y"
            OECORELOCALCONF=$input
            OECORELOCALCONFPATH=$sourcedir/$OECORELOCALCONF
        else
            echo "ERROR: Could not find the inputted sample file: $input"
            exit 1
        fi
    done
}


config_oecorelayerconf() {
cat << EOM


################################################################################
The bblayers.conf configuration file has been created for you with some
default values.  Please verify the contents of your conf/bblayers.conf
file for correctness.

NOTE: Any additional entries to this file will be lost if the $0
      script is run again.  To add entries permanently to this file
      please add them to the config file used and rerun the
      $0 script.

EOM
    # First copy the template file
    cp -f $OECORELAYERCONFPATH $confdir/bblayers.conf

    # Now add the layers we have configured to the BBLAYERS variable
cat >> $confdir/bblayers.conf << EOM

# Layers configured by oe-core-setup script
BBLAYERS += " \\
EOM
    for l in $layers
    do
        printf "\t$l \\%b" "\n" >> $confdir/bblayers.conf
    done
    echo "\"" >> $confdir/bblayers.conf
}


config_oecorelocalconf() {
cat << EOM


################################################################################
The local.conf configuration file has been created for you with some
default values.  Please verify the contents of your conf/local.conf
file for correctness.

By default the number of threads used by the build is set to the number
of CPUs found on your system.

NOTE: You will probably want to change the default MACHINE setting in the
      local.conf file to the machine you are trying to build.

EOM
    
    if [ -e $confdir/local.conf ]
    then
        echo "WARNING: Found existing $confdir/local.conf"
        echo "Saving a backup to $confdir/local.conf.bak" 
        cp -f $confdir/local.conf $confdir/local.conf.bak
    fi

    # First copy the template file
    cp -f $OECORELOCALCONFPATH $confdir/local.conf

    # set the number of threads
    threads=`cat /proc/cpuinfo | grep -c processor`
    sed -i "s/^PARALLEL_MAKE.*/PARALLEL_MAKE = \"-j ${threads}\"/" $confdir/local.conf
    sed -i "s/^BB_NUMBER_THREADS.*/BB_NUMBER_THREADS = \"${threads}\"/" $confdir/local.conf

    # Find if old DL_DIR was set
    if [ -e $confdir/local.conf.bak ]
    then
        old_dldir=`cat $confdir/local.conf.bak | grep -e "^DL_DIR =" | sed 's|DL_DIR = ||' | sed 's/"//g'`
    else
        old_dldir="$oebase/downloads"
    fi

    # If command line option was not set use the old dldir
    if [ "x$dldir" = "x" ]
    then
        dldir=$old_dldir
    fi

    sed -i "s|^DL_DIR.*|DL_DIR = \"${dldir}\"|" $confdir/local.conf
}


create_setenv_file() {
cat << EOM


################################################################################
A setenv file has been created for you in the conf directory.  Please verify
The contents of this file.  Once you have verified the contents please source
this file to configure your environment for building:

    . conf/setenv

You can then start building using the bitbake command.  You will likely want
to set the MACHINE option if you have not done so in your local.conf file.

For example:
    MACHINE=xxxxx bitbake <target>

Common targets are:
    core-image-minimal
    core-image-sato
    meta-toolchain
    meta-toolchain-sdk
    adt-installer
    meta-ide-support
EOM

    # Write the setenv file
cat > $confdir/setenv << EOM
# Set OEBASE to where the build and source directories reside
# NOTE: Do NOT place a trailing / on the end of OEBASE.
export OEBASE=${oebase}

# try to find out bitbake directory
BITBAKEDIR=\`find \${OEBASE}/sources -name "*bitbake*"\`
for f in \${BITBAKEDIR}
do
    if [ -d \${f}/bin ]
    then
        PATH="\${f}/bin:\$PATH"
    fi
done

# check for any scripts directories in the top-level of the repos and add those
# to the PATH
SCRIPTS=\`find \${OEBASE}/sources -maxdepth 2 -name "scripts" -type d\`
for s in \${SCRIPTS}
do
    PATH="\${s}:\$PATH"
done

unset BITBAKEDIR
unset SCRIPTS
export PATH
export BUILDDIR=${builddir}
export BB_ENV_EXTRAWHITE="MACHINE DISTRO TCMODE TCLIBC http_proxy ftp_proxy https_proxy all_proxy ALL_PROXY no_proxy SSH_AGENT_PID SSH_AUTH_SOCK BB_SRCREV_POLICY SDKMACHINE BB_NUMBER_THREADS PARALLEL_MAKE GIT_PROXY_COMMAND GIT_PROXY_IGNORE SOCKS5_PASSWD SOCKS5_USER OEBASE META_SDK_PATH TOOLCHAIN_TYPE TOOLCHAIN_BRAND TOOLCHAIN_BASE TOOLCHAIN_PATH TOOLCHAIN_PATH_ARMV5 TOOLCHAIN_PATH_ARMV7 TOOLCHAIN_PATH_ARMV8 EXTRA_TISDK_FILES TISDK_VERSION ARAGO_BRAND ARAGO_RT_ENABLE ARAGO_SYSTEST_ENABLE ARAGO_KERNEL_SUFFIX TI_SECURE_DEV_PKG_CAT TI_SECURE_DEV_PKG_AUTO ARAGO_SYSVINIT"
EOM
}


build_repo_line() {
    # clean up the layers to remove the repository name and add : divider
    temp_layers=""
    for l in `echo $repo_layers | sed "s:${name}::" | sed -e 's:^\/::'`
    do
        temp_layers="$temp_layers""`echo $l | sed "s:${name}\/::"`:"
    done

    # Lastly clean off any trailing :
    temp_layers=`echo $temp_layers | sed 's/:$//'`

    echo "$name,$uri,$branch,$commit,layers=$temp_layers"
}

###############
# Main Script #
###############

# Parse the input options
while getopts :if:o:d:b:h arg
do
    case $arg in
        i ) interactive="y";;
        f ) inputfile="$OPTARG";;
        o ) outputfile="$OPTARG";;
        d ) dldir="$OPTARG";;
        b ) oebase="$OPTARG";;
        h ) usage;;
    esac
done

# create passed in directory if it doesn't exist
mkdir -p $oebase

# retrive the absolute path to the oebase directory incase
# a relative path is passed in
cd $oebase
oebase=`pwd`
cd -

# Populate the following variables depending on the value of oebase
sourcedir="$oebase/sources"
builddir="$oebase/build"
confdir="$builddir/conf"

check_input

if [ "x$inputfile" != "x" ]
then
    parse_input_file
fi

if [ "x$interactive" = "xy" ]
then
    cont="y"
    while [ "x$cont" = "xy" -o "x$cont" = "xY" ]
    do
        # clean up the variables for each repo
        name=""
        uri=""
        branch=""
        commit=""
        repo_layers=""

        configure_repo

        if [ "$?" != "0" ]
        then
            continue
        fi

        # Create the repository line corresponding to the selections given.
        # In the case that no layers= option was passed then this will
        # create the layers= option corresponding to all layers being selected.
        repo_line=`build_repo_line`

        # Save the line in the output variable for if we create an output file
        output="$output""$repo_line\n"

        save_layers

        echo ""
        echo ""
        echo "Would you like to configure another repository? [y/n] "
        read cont
    done
fi

get_oecorelayerconf
config_oecorelayerconf

get_oecorelocalconf
config_oecorelocalconf

if [ "x$outputfile" != "x" ]
then
    # make sure that the directory for the output file exists
    cd $oebase
    dir=`dirname $outputfile`
    if [ ! -d $dir ]
    then
        mkdir -p $dir
    fi
    printf "$output\n" > $outputfile
    echo "Output file is $outputfile"
fi

create_setenv_file
