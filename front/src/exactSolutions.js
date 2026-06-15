export function exactValue(number, x, x0, y0) {
    switch (Number(number)) {
        case 1:
            return Math.pow(x + 1, 2) + (y0 - Math.pow(x0 + 1, 2)) * Math.exp(x - x0);

        case 2:
            return Math.tan(x * x - x0 * x0 + Math.atan(y0));

        case 3:
            return (Math.sin(x) - Math.cos(x)) / 2.0
                + (y0 - (Math.sin(x0) - Math.cos(x0)) / 2.0) * Math.exp(-(x - x0));

        case 4:
            return y0 * Math.exp(Math.sin(x) - Math.sin(x0));

        default:
            return NaN;
    }
}

export function buildExactChartData(number, x0, xn, y0, points = 700) {
    const data = [];

    for (let i = 0; i <= points; i++) {
        const x = x0 + (xn - x0) * i / points;
        const y = exactValue(number, x, x0, y0);

        if (Number.isFinite(x) && Number.isFinite(y)) {
            data.push({ x, y });
        }
    }

    return data;
}