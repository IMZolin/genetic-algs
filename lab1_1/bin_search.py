import bisect
from typing import List

import numpy as np

### Classical binary search: T(n) = O(logN); S(n) = O(1)

def binary_search(arr: List[int], target: int) -> int:
    l, r = 0, len(arr) - 1
    while l <= r:
        mid = (l + r) // 2
        if arr[mid] == target:
            return mid
        elif arr[mid] < target:
            l = mid + 1
        else:
            r = mid - 1
    return -1

def binary_search_recursive(arr: List[int], target: int, lo=0, hi=None) -> int:
    if hi is None:
        hi = len(arr) - 1
    if lo > hi:
        return -1

    mid = (lo + hi) // 2
    if arr[mid] == target:
        return mid

    if arr[mid] < target:
        return binary_search_recursive(arr, target, mid + 1, hi)
    else:
        return binary_search_recursive(arr, target, lo, mid - 1)

def binary_search_localvars(arr: List[int], target: int) -> int:
    lo = 0
    hi = len(arr) - 1
    local_arr = arr
    while lo <= hi:
        mid = (lo + hi) >> 1
        val = local_arr[mid]
        if val == target:
            return mid
        elif val < target:
            lo = mid + 1
        else:
            hi = mid - 1
    return -1

def binary_search_bisect(arr: List[int], target: int) -> int:
    pos = bisect.bisect_left(arr, target)
    if pos != len(arr) and arr[pos] == target:
        return pos
    return -1

def binary_search_numpy(arr_np: np.ndarray, target: int) -> int:
    pos = np.searchsorted(arr_np, target)
    if pos < len(arr_np) and arr_np[pos] == target:
        return int(pos)
    return -1
