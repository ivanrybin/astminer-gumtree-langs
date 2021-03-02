#include <algorithm>
#include <cmath>
#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <iomanip>
#include <iostream>
#include <map>
#include <queue>
#include <set>
#include <sstream>
#include <string>
#include <vector>
#include <list>
#include <cassert>
#include <queue>
#include <deque>

using namespace std;
template <class _T> inline _T sqr(const _T& x) { return x * x; }
template <class _T> inline _T ABS(const _T& x) { return (x<0)?-x:x;}
template <class _T> inline string tostr(const _T& a) { ostringstream os(""); os << a; return os.str(); }
template <class _T> inline istream& operator << (istream& is, const _T& a) { is.putback(a); return is; }
template <class _T> inline _T gcd(const _T &a, const _T &b) {
    _T t;
    while (!(b == 0)) {
        t = a % b;
        a = b;
        b = t;
    }
    return a;
}

typedef long double ld;

const ld EPS = 1e-11;

typedef unsigned uns;
typedef signed long long i64;
typedef unsigned long long u64;
typedef set < int > SI;
typedef vector < ld > VD;
typedef vector < int > VI;
typedef vector < bool > VB;
typedef vector < string > VS;
typedef map < string, int > MSI;
typedef map < string, void * > MSV;

static bool const _debug_ = false;

inline uns func1() {
    uns curr;
    scanf("%u", &curr);
    return curr;
}

inline void func2(uns &one, uns &two) {
    scanf("%u%u", &one, &two);
}


inline void func3(int &one, int &two) {
    scanf("%d%d", &one, &two);
}

inline double func4() {
    double curr;
    scanf("%lf", &curr);
    return curr;
}

inline string func5() {
    string curr;
    cin >> curr;
    return curr;
}

inline string func6() {
    string curr;
    getline(cin, curr);
    return curr;
}

inline void func7(string const &in, VS &out, char delim = ' ') {
}

int func8(deque<double> &N, deque<double> &K) {
    int score = 0;
    return score;
}

int func9(deque<double> &N, deque<double> &K) {
    int score = 0;
    return score;
}

inline void func10(uns testNum) {
    cout << endl;
}

int main() {
    uns T = func1();
    return 0;
}
