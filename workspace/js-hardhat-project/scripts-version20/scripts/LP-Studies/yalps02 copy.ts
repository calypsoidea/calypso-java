
import { solve } from "yalps"

import { lessEq, equalTo, greaterEq, inRange } from "yalps"

import { Model, Constraint, Coefficients, OptimizationDirection, Options, Solution } from "yalps"

const model = {
    direction: "maximize" as const,
    objective: "profit",
    constraints: {
      first: { max: 100 },
      second: { max: 400 }, // labor should be <= 110
      third: lessEq(3200), // you can use the helper functions instead
    },
    variables: {
      a: { first: 1, second: 5, third: 40, profit: 70 },
      b: { first: 1, second: 4, third: 20, profit: 210 },
      c: { first: 1, second: 4, third: 30, profit: 140 },
    },
    //integers: ["table", "dresser"], // these variables must have an integer value in the solution
    integers: false,
  }
  
  const solution = solve(model)
  // { status: "optimal", result: 14400, variables: [ ["table", 8], ["dresser", 3] ] }

  console.log(solution)