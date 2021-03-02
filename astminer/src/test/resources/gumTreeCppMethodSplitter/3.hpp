#pragma once
#include <iostream>
#include <algorithm>

namespace control_01 {

    template <typename T>
    class vector {
    public:

        vector();

        ~vector();

        vector& operator=(vector&& other);


        vector() noexcept : size_(0), capacity_(0), data_(nullptr) {}

        ~vector() {
            delete data_;
        }

        vector(size_t size, T& default_value = nullptr) noexcept : size_(size), capacity_(size_), data_(new T[size]{}) {
        }

        vector(const vector& other) : size_(other.size_), capacity_(other.capacity_), data_(new T[other.capacity_]{}){
        }

        vector(vector&& other) noexcept : size_(other.size_), capacity_(other.capacity_), data_(other.data_) {
        }

        vector& operator=(const vector& other) {
        }

        vector& operator=(vector&& other) noexcept {
            if (&other != this) {
                swap(other);
                other.size_     = 0;
                other.capacity_ = 0;
                other.data_     = nullptr;
            }
            return *this;
        }

        size_t size() const noexcept {
            return size_;
        }

        size_t capacity() const noexcept {
            return capacity_;
        }

        bool empty() const noexcept {
            return size_;
        }

        T& front() {
            return data_[0];
        }

        const T& front() const {
            return data_[0];
        }

        T& back() {
            return data_[size_ - 1];
        }

        const T& back() const {
            return data_[size_ - 1];
        }

        T& at(size_t pos) {
            return get_item(pos);
        }

        const T& at(size_t pos) const {
            return get_item(pos);
        }

        T& operator[](size_t pos) {
            return get_item(pos);
        }

        const T& operator[](size_t pos) const {
            return get_item(pos);
        }

        void push_back(const T& value) {
        }

        void push_back(T&& value) {
        }

        void pop_back() {
            (size_) ? --size_: size_;
        }

        void clear() {
            size_ = 0;
        }

        void reserve(size_t new_capacity) {
        }

        void swap(vector& other) {
            if (&other != this) {
                std::swap(other, *this);
            }
        }

        explicit operator bool() const noexcept {
            return size_;
        }

        using value_type = T;

        struct INNER_STRUCT {
            void hello() {

            }
        };

    private:
        T& get_item(size_t pos) {
            if (pos >= size_) {
                throw std::out_of_range("");
            }
            return data_[pos];
        }

        size_t size_;
        size_t capacity_;
        T *    data_;
    };
}
