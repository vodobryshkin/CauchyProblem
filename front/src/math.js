export function formatNumber(value, digits = 8) {
  if (value === null || value === undefined || Number.isNaN(Number(value))) {
    return "—";
  }

  const number = Number(value);

  if (!Number.isFinite(number)) {
    return String(number);
  }

  if (Math.abs(number) >= 1_000_000 || Math.abs(number) < 0.000001 && number !== 0) {
    return number.toExponential(6);
  }

  return number.toLocaleString("ru-RU", {
    maximumFractionDigits: digits
  });
}

export function absoluteError(approx, exact) {
  return Math.abs(Number(exact) - Number(approx));
}
