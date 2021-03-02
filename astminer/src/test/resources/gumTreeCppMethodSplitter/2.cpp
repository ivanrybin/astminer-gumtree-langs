/*
    Huffman coding.
*/
#include <iostream>
#include <fstream>
#include <queue>
#include <bitset>
#include <algorithm>
#include <experimental/filesystem>

#include "huffman.hpp"

namespace fs = std::experimental::filesystem;

bool operator < (const CharData& lhs, const CharData& rhs) {
    return lhs.frequency > rhs.frequency;
}

/*
    comment
*/
bool codes_sort(const std::pair<unsigned char, std::string>& lhs, const std::pair<unsigned char, std::string>& rhs) {
    return lhs.second < rhs.second;
}

size_t get_file_size(const std::string& file_name) {
    return fs::file_size(fs::path(file_name));
}

const char* get_char_content(const std::string& file_name,
                                        size_t  size)
{

     char * buffer = new char [size + 1]{};
     return buffer;
}

std::vector<std::string> get_alphabet_tree_str(const char*      content,
                                                   size_t       content_size,
                                                   size_t&      alphabet_size,
                                                   size_t&      tree_size) {
    return {};
}

std::string get_out_str(const std::string&      alphabet,
                        const std::string&      tree,
                        const std::string&      str,
                                   size_t       bits_tree,
                                   size_t       bits_data) {
    return out + alphabet + tree + "!T^" + str; // !T^ -- separator between tree and encoded string
}

void print_statistics(size_t                input_size,
                      size_t                output_size,
                      size_t                help_size,
                      const char_code_map&  chars_codes,
                      bool                  is_console) {
}

void write_file(const std::string&      file_name,
                const std::string&      output_str) {
}

void free_memory(std::vector<const Node*>& nodes) {

    for (const auto& item: nodes) {
        delete item;
    }
    nodes = {};
}

std::vector<CharData> chars_frequencies(const char*     content,
                                            size_t      size) {
    char_freq_map chars_freq_map{};
    return chars_freq_vec;
}

std::string huffman_decoding(const std::string&     encoded_str,
                             const Node*            root,
                             char_code_map&         chars_codes) {
    std::stringstream ss{};
    return ss.str();
}

char_code_map huffman_encoding(const std::vector<CharData>& input_chars) {
    char_code_map encoded_chars{};
    return encoded_chars; 
}

Node* build_tree_with_map(const char_code_map& encoded_chars, memory_vector& nodes) {
    return nullptr;
}

void dfs(Node*                  root,
         std::stringstream&     tree,
         std::string&           alphabet) {
}

std::string encode_tree(Node*           root,
                        size_t&         bits_tree,
                        std::string&    alphabet) {
    std::string bytes_encoded_tree{};
    return bytes_encoded_tree;
}

Node* build_alphabet_tree(const std::string&    alphabet,
                          const std::string&    encoded_tree,
                          memory_vector&        nodes) {
    Node* root = new Node;
    return root;
}

std::string encode_string(const char*       content,
                         size_t             size,
                         char_code_map&     char_freq_map,
                         size_t&            bits_data) {
    std::string bytes_encoded_str{};
    return bytes_encoded_str;
}

void encoding(const char*           input_str,
              size_t                input_size,
              const std::string&    output_file,
              bool                  is_console,
              memory_vector&        nodes) {
}

void decoding(std::vector<std::string>  v_alphabet_tree_str,
              size_t                    input_size,
              size_t                    alpha_size,
              size_t                    tree_size,
              std::string               output_file,
              bool                      is_console,
              memory_vector&            nodes) {
}
