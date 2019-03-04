# Add all feature options vim supports
PACKAGECONFIG[tiny] = "--with-features=tiny,,,"
PACKAGECONFIG[small] = "--with-features=small,,,"
PACKAGECONFIG[normal] = "--with-features=normal,,,"
PACKAGECONFIG[big] = "--with-features=big,,,"
PACKAGECONFIG[huge] = "--with-features=huge,,,"

# By default build small system
PACKAGECONFIG += "tiny"
