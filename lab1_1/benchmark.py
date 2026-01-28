from collections import defaultdict
import json
import random
import time
import resource
from typing import Callable, Dict, List
from matplotlib import pyplot as plt
import numpy as np
from bin_search import (
    binary_search, 
    binary_search_bisect,
    binary_search_localvars,
    binary_search_numpy,
    binary_search_recursive
)


def get_memory_bytes() -> int:
    return resource.getrusage(resource.RUSAGE_SELF).ru_maxrss * 1024

def benchmark(
    name: str,
    func: Callable,
    arr,
    queries: List[int]
) -> Dict:
    mem_before = get_memory_bytes()
    t0_cpu = time.process_time()
    t0 = time.perf_counter()

    for q in queries:
        func(arr, q)

    t1 = time.perf_counter()
    t1_cpu = time.process_time()
    mem_after = get_memory_bytes()

    return {
        "method": name,
        "time_s": t1 - t0,
        "cpu_s": t1_cpu - t0_cpu,
        "mem_before_bytes": mem_before,
        "mem_after_bytes": mem_after,
        "queries": len(queries),
        "array_size": len(arr),
    }


def run_all_benchmarks(array_sizes: list, num_queries: int):
    results = []

    for n in array_sizes:
        print(f"\n=== Benchmark n = {n} ===")
        arr = list(range(n))
        arr_np = np.array(arr, dtype=np.int64)

        queries = [random.randint(-n, 2 * n) for _ in range(num_queries)]
        tests = [
            ("iterative", binary_search, arr),
            ("recursive", binary_search_recursive, arr),
            ("localvars", binary_search_localvars, arr),
            ("bisect", binary_search_bisect, arr),
            ("numpy", binary_search_numpy, arr_np),
        ]

        for name, func, dataset in tests:
            res = benchmark(name, func, dataset, queries)
            results.append(res)
            print(
                f"Completed: n={n}, method={name}, "
                f"time={res['time_s']:.4f}s, cpu={res['cpu_s']:.4f}s"
            )
    return results

def draw_plots_from_json(
    json_path: str = "results.json",
    save_path: str | None = None
):
    """
    Draws performance plots from JSON benchmark results with logarithmic axes.
    """
    with open(json_path, "r") as f:
        data = json.load(f)

    grouped: Dict[str, List[Dict]] = defaultdict(list)
    for entry in data:
        grouped[entry["method"]].append(entry)

    fig, axs = plt.subplots(3, 1, figsize=(10, 15))
    fig.suptitle("Binary Search Benchmark Results (Logarithmic Axes)", fontsize=16)

    for method, entries in grouped.items():
        entries_sorted = sorted(entries, key=lambda x: x["array_size"])
        axs[0].plot(
            [e["array_size"] for e in entries_sorted],
            [e["time_s"] for e in entries_sorted],
            marker="o",
            label=method
        )

    axs[0].set_title("Total Time vs Array Size")
    axs[0].set_xlabel("Array Size (log)")
    axs[0].set_ylabel("Time (seconds, log)")
    axs[0].set_xscale("log")
    axs[0].set_yscale("log")
    axs[0].grid(True, which="both")
    axs[0].legend()

    for method, entries in grouped.items():
        entries_sorted = sorted(entries, key=lambda x: x["array_size"])
        axs[1].plot(
            [e["array_size"] for e in entries_sorted],
            [e["cpu_s"] for e in entries_sorted],
            marker="s",
            label=method
        )

    axs[1].set_title("CPU Time vs Array Size")
    axs[1].set_xlabel("Array Size (log)")
    axs[1].set_ylabel("CPU Time (seconds, log)")
    axs[1].set_xscale("log")
    axs[1].set_yscale("log")
    axs[1].grid(True, which="both")
    axs[1].legend()

    for method, entries in grouped.items():
        entries_sorted = sorted(entries, key=lambda x: x["array_size"])
        axs[2].plot(
            [e["array_size"] for e in entries_sorted],
            [e["mem_after_bytes"] - e["mem_before_bytes"] for e in entries_sorted],
            marker="^",
            label=method
        )

    axs[2].set_title("Memory Change vs Array Size")
    axs[2].set_xlabel("Array Size (log)")
    axs[2].set_ylabel("Δ Memory (bytes, log)")
    axs[2].set_xscale("log")
    axs[2].set_yscale("log")
    axs[2].grid(True, which="both")
    axs[2].legend()

    plt.tight_layout(rect=[0, 0, 1, 0.96])
    if save_path:
        plt.savefig(save_path, dpi=300)
        print(f"Saved plot to {save_path}")
    else:
        plt.show()
