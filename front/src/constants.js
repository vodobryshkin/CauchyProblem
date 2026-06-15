export const METHOD_NAMES = {
  euler: "Эйлера",
  runge: "Рунге-Кутта 4 порядка",
  miln: "Милна"
};

export const METHOD_OPTIONS = [
  { value: "euler", label: "Эйлера" },
  { value: "runge", label: "Рунге-Кутта 4 порядка" },
  { value: "miln", label: "Милна" }
];

export const EQUATIONS = {
  1: {
    label: "y' = y - x² + 1",
    odeLatex: "y' = y - x^2 + 1",
    exactLatex: "y = (x + 1)^2 + \\left(y_0 - (x_0 + 1)^2\\right)e^{x - x_0}"
  },
  2: {
    label: "y' = 2x / (1 + y)",
    odeLatex: "y' = \\frac{2x}{1 + y}",
    exactLatex: "y = -1 + \\sqrt{(1 + y_0)^2 + 2(x^2 - x_0^2)}"
  },
  3: {
    label: "y' = sin(x) - y",
    odeLatex: "y' = \\sin x - y",
    exactLatex: "y = \\frac{\\sin x - \\cos x}{2} + \\left(y_0 - \\frac{\\sin x_0 - \\cos x_0}{2}\\right)e^{-(x - x_0)}"
  },
  4: {
    label: "y' = y · cos(x)",
    odeLatex: "y' = y\\cos x",
    exactLatex: "y = y_0 e^{\\sin x - \\sin x_0}"
  }
};

export const EQUATION_OPTIONS = Object.entries(EQUATIONS).map(([value, equation]) => ({
  value,
  label: equation.label
}));