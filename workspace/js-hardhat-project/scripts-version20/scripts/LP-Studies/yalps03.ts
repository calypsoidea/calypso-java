
import { solve } from "yalps"

import { lessEq, equalTo, greaterEq, inRange } from "yalps"

import { Model, Constraint, Coefficients, OptimizationDirection, Options, Solution } from "yalps"

const model = {
    direction: "minimize" as const,
    objective: "profit",
    constraints: {
      first: { min: 700 },
      second: { min: 400 }, // labor should be <= 110
    },
    variables: {
      x1: { first: 1, second: 0, profit: 2 },
      x2: { first: 0, second: 1, profit: 20 },
      x3: { first: 1, second: 0, profit: 1 },
      x4: { first: 1, second: 1, profit: 11 },
      x5: { first: 2, second: 1, profit: 12 },
    },
    //integers: ["table", "dresser"], // these variables must have an integer value in the solution
    integers: false,
  }
  
  const solution = solve(model)
  
  console.log(solution)