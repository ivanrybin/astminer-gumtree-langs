#pragma once

#include <iostream>

namespace ts {
    using namespace details;

    template<int m = 0, int s = 0, int g = 0>
    using dimension = int_list<m, s, g>;

    template<typename M>
    long double get_value(const M& metric) {
        return metric.inner_get_value();
    }

    // quantity -----------------------------------------------------------------------------------
    template<typename D>
    struct quantity;

    template<int ...XS>
    struct quantity<int_list<XS...>> {
        static constexpr double type_constant = 1;

        using list_type = int_list<XS...>;

        template<typename Q>
        friend long double get_value(const Q& q);

        quantity() = default;
        explicit quantity(long double value): value(value) {}
        quantity(const quantity& other) : value(other.value) {}
        quantity(quantity&& other) noexcept = default;

        quantity& operator=(long double other_value) {
            value = other_value;
            return *this;
        }
        quantity& operator=(const quantity& other) {
            if (&other != this) {
                value = other.value;
            }
            return *this;
        }
        quantity& operator=(quantity&& other) noexcept {
            if (&other != this) {
                value = other.value;
            }
            return *this;
        }
        // arithmetic
        quantity& operator+=(const quantity& other) {
            value += other.value;
            return *this;
        }
        quantity& operator-=(const quantity& other) {
            value -= other.value;
            return *this;
        }
        quantity& operator*=(const quantity& other) {
            value *= other.value;
            return *this;
        }
        quantity& operator/=(const quantity& other) {
            value /= other.value;
            return *this;
        }
        template<typename T, typename = std::enable_if_t<std::is_arithmetic_v<T>, void>>
        quantity& operator*=(T other_value) {
            value *= other_value;
            return *this;
        }
        template<typename T, typename = std::enable_if_t<std::is_arithmetic_v<T>, void>>
        quantity& operator/=(T other_value) {
            value /= other_value;
            return *this;
        }
        quantity& operator-() {
            value *= -1;
            return *this;
        }
        // booleans
        template<typename D>
        friend bool operator==(const quantity<D>& lhs, const quantity<D>& rhs);
        template<typename D>
        friend bool operator!=(const quantity<D>& lhs, const quantity<D>& rhs);
        template<typename D>
        friend bool operator<=(const quantity<D>& lhs, const quantity<D>& rhs);
        template<typename D>
        friend bool operator<(const quantity<D>& lhs, const quantity<D>& rhs);
        template<typename D>
        friend bool operator>(const quantity<D>& lhs, const quantity<D>& rhs);
        template<typename D>
        friend bool operator>=(const quantity<D>& lhs, const quantity<D>& rhs);
    protected:
        long double inner_get_value() const {
            return value;
        }

        long double value = 0;
    }; // quantity
    
    // quantity aliases ---------------------------------------------------------------------------
    using meter_t   = quantity<dimension<1, 0, 0>>;
    using second_t  = quantity<dimension<0, 1, 0>>;
    using gram_t    = quantity<dimension<0, 0, 1>>;
    
    // operators quantity -------------------------------------------------------------------------
    template<typename D>
    quantity<D> operator+(quantity<D> lhs, const quantity<D>& rhs) {
        lhs += rhs;
        return lhs;
    }

    template<typename D>
    quantity<D> operator-(quantity<D> lhs, const quantity<D>& rhs) {
        lhs -= rhs;
        return lhs;
    }

    // quantity + integral types ------------------------------------------------------------------
    template<typename D, typename T, typename = std::enable_if_t<std::is_arithmetic_v<T>, void>>
    quantity<D> operator*(quantity<D> lhs, T x) {
        lhs *= x;
        return lhs;
    }

    template<typename D, typename T, typename = std::enable_if_t<std::is_arithmetic_v<T>, void>>
    quantity<D> operator/(quantity<D> lhs, T x) {
        lhs /= x;
        return lhs;
    }

    template<typename D, typename T, typename = std::enable_if_t<std::is_arithmetic_v<T>, void>>
    quantity<D> operator*(T x, quantity<D> rhs) {
        rhs *= x;
        return rhs;
    }

    meter_t operator"" _m(unsigned long long int value) {
        return meter_t(value);
    }

    second_t operator"" _s(unsigned long long int value) {
        return second_t(value);
    }

    kilometer_t operator"" _km(unsigned long long int value) {
        return kilometer_t(value);
    }

}
