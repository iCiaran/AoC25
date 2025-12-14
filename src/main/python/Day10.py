from dataclasses import dataclass
from z3 import *


@dataclass
class Machine:
    buttons: list[list[int]]
    joltages: list[int]


def parse_input(filename: str) -> list[Machine]:
    with open(filename, "r") as file:
        lines = file.read().splitlines()

    machines = []

    for line in lines:
        split = line.split(" ")
        buttons = [[int(b) for b in p[1:-1].split(",")] for p in split[1:-1]]
        joltages = [int(j) for j in split[-1][1:-1].split(",")]
        machines.append(Machine(buttons, joltages))

    return machines


def find_minimum(machine: Machine) -> int:
    presses = [Int(f"x{i}") for i in range(len(machine.buttons))]

    opt = Optimize()

    for press in presses:
        opt.add(press >= 0)

    for joltage_index, joltage in enumerate(machine.joltages):
        presses_which_impact = [
            button_index
            for button_index in range(len(machine.buttons))
            if joltage_index in machine.buttons[button_index]
        ]

        opt.add(Sum(presses[p] for p in presses_which_impact) == joltage)

    opt.minimize(Sum(presses))

    if opt.check() == sat:
        model = opt.model()
        return sum(model[press].as_long() for press in presses)
    else:
        print("No solution found")
        exit(1)


def solve(parsed_input: list[Machine]) -> int:
    return sum(find_minimum(machine) for machine in parsed_input)


if __name__ == "__main__":
    machines = parse_input("inputs/10.txt")
    result = solve(machines)
    print(result)
