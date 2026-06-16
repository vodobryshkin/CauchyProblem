export function validateForm(form) {
  const errors = [];

  const requiredFields = [
    form.number,
    form.method,
    form.y0,
    form.x0,
    form.xn,
    form.h,
    form.epsilon
  ];

  if (requiredFields.some(value => value === "" || value === null || value === undefined)) {
    errors.push("Заполните все поля.");
    return errors;
  }

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
    errors.push("Шаг должен быть положительным.");
  }

  if (Number.isFinite(epsilon) && epsilon <= 0) {
    errors.push("Точность погрешности должна быть положительной.");
  }

  if (number === 2 && [y0, x0, xn].every(Number.isFinite) && !isSecondEquationIntervalAllowed(x0, xn, y0)) {
    errors.push("Для данного уравнения решение на выбранном интервале доходит до y = -1.");
  }

  return errors;
}

function isSecondEquationIntervalAllowed(x0, xn, y0) {
  if (y0 <= -1) {
    return false;
  }

  const minSquare = x0 <= 0 && xn >= 0 ? 0 : Math.min(x0 * x0, xn * xn);
  const minRadicand = Math.pow(1 + y0, 2) + 2 * (minSquare - x0 * x0);

  return minRadicand > 0;
}