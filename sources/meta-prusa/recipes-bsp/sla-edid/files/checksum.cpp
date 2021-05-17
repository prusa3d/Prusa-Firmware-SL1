#include <cstdint>
#include <fstream>
#include <iterator>

using namespace std;

template<class InputIt, class T>
T accumulate_n(InputIt it, size_t n, T init)
{
	for (size_t i = 0; i < n; ++i)
		init += *it++;
	return init;
}

int main(int argc, char **argv) {
	fstream file(argv[1], ios::binary | ios::in | ios::out);
	uint8_t result = 0xff;
	result -= accumulate_n(istreambuf_iterator<char>(file), 127, uint8_t(0xff));
	file.put(result);
	return 0;
}
