#pragma once

#include <vector>
#include <iterator>

void func_decl();

void func_decl(int x);

std::string func_decl(int x, int y);

namespace cls_05 {

    template <typename It>
    using diff_type = typename std::iterator_traits<It>::difference_type;

    template<typename It>
    diff_type<It> distance(It first, It second, std::bidirectional_iterator_tag) {
        auto dist = 0;
        while (first != second) {
            ++dist;
            ++first;
        }
        return dist;
    }
}
