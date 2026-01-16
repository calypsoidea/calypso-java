
import { solve } from "yalps"

import { lessEq, equalTo, greaterEq, inRange } from "yalps"

import { Model, Constraint, Coefficients, OptimizationDirection, Options, Solution } from "yalps"

const model = {
    direction: "maximize" as const,
    objective: "profit",
    constraints: {
      first: { max: 1500 },
      second: { max: 100 }, 
      third: { max: 100 }
    },
    variables: {
      x1: { first: 100, second: 7, third: 3, profit: 60 },
      x2: { first: 100, second: 5, third: 5, profit: 60 },
      x3: { first: 100, second: 3, third: 10, profit: 90 },
      x4: { first: 100, second: 10, third: 15, profit: 90 }
    },
    //integers: ["table", "dresser"], // these variables must have an integer value in the solution
    integers: false,
  }
  
  const solution = solve(model)
  // { status: "optimal", result: 14400, variables: [ ["table", 8], ["dresser", 3] ] }

  console.log(solution)