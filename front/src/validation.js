export function validateForm(form) {
  const errors = [];

  const number = Number(form.number);
  const y0 = Number(form.y0);
  const x0 = Number(form.x0);
  const xn = Number(form.xn);
  const h = Number(form.h);
  const epsilon = Number(form.epsilon);

  if (!Number.isInteger(number) || number < 1 || number > 4) {
    errors.push("Номер уравнения должен быть от 1 до 4.");
  }

  if (!form.method) {
    errors.push("Метод должен быть выбран.");
  }

  if (![y0, x0, xn, h, epsilon].every(Number.isFinite)) {
    errors.push("Все числовые поля должны быть конечными числами.");
  }

  if (Number.isFinite(x0) && Number.isFinite(xn) && x0 >= xn) {
    errors.push("Левая граница должна быть меньше правой.");
  }

  if (Number.isFinite(h) && h <= 0) {
    errors.push("Шаг h должен быть положительным.");
  }

  if (Number.isFinite(epsilon) && epsilon <= 0) {
    errors.push("Точность epsilon должна быть положительной.");
  }

  if (number === 2 && [y0, x0, xn].every(Number.isFinite) && !isSecondEquationIntervalAllowed(x0, xn, y0)) {
    errors.push("Для уравнения y' = 2x(1 + y²) выбранный интервал пересекает точку разрыва решения.");
  }

  return errors;
}

function isSecondEquationIntervalAllowed(x0, xn, y0) {
  const margin = 1e-6;
  const minSquare = x0 <= 0 && xn >= 0 ? 0 : Math.min(x0 * x0, xn * xn);
  const maxSquare = Math.max(x0 * x0, xn * xn);
  const shift = Math.atan(y0) - x0 * x0;
  const minArgument = minSquare + shift - margin;
  const maxArgument = maxSquare + shift + margin;
  const firstAsymptoteIndex = Math.ceil((minArgument - Math.PI / 2) / Math.PI);
  const lastAsymptoteIndex = Math.floor((maxArgument - Math.PI / 2) / Math.PI);

  return firstAsymptoteIndex > lastAsymptoteIndex;
}
