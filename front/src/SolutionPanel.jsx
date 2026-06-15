import { METHOD_NAMES } from "./constants.js";
import { formatNumber } from "./math.js";

export default function SolutionPanel({ solution }) {
  return (
      <section>
        <h2>Результат</h2>

        <ResultParameters solution={solution} />
        <ResultTable solution={solution} />
      </section>
  );
}

function ResultParameters({ solution }) {
  return (
      <article>
        <h3>Параметры расчёта</h3>

        <table className="params-table">
          <tbody>
          <tr>
            <th>Метод</th>
            <td>{METHOD_NAMES[solution.method] ?? solution.method}</td>
          </tr>
          <tr>
            <th>Уравнение</th>
            <td>{solution.f}</td>
          </tr>
          <tr>
            <th>Начальное условие</th>
            <td>
              x₀ = {formatNumber(solution.x0)}, y₀ = {formatNumber(solution.y0)}
            </td>
          </tr>
          <tr>
            <th>Интервал</th>
            <td>
              [{formatNumber(solution.x0)}; {formatNumber(solution.xn)}]
            </td>
          </tr>
          <tr>
            <th>Фактический шаг</th>
            <td>{formatNumber(solution.h)}</td>
          </tr>
          <tr>
            <th>Заданная точность</th>
            <td>{formatNumber(solution.epsilon)}</td>
          </tr>
          <tr>
            <th>Оценка погрешности</th>
            <td>{formatNumber(solution.error_estimate)}</td>
          </tr>
          <tr>
            <th>Точность достигнута</th>
            <td>{solution.accuracy_reached ? "да" : "нет"}</td>
          </tr>
          </tbody>
        </table>
      </article>
  );
}

function ResultTable({ solution }) {
  const xRow = solution.table.x_row ?? [];
  const yRow = solution.table.y_row ?? [];
  const exactSolution = solution.exact_solution ?? [];

  return (
      <article>
        <h3>Таблица значений</h3>

        <div className="table-scroll">
          <table className="values-table">
            <colgroup>
              <col className="index-column" />
              <col className="x-column" />
              <col className="approx-column" />
              <col className="exact-column" />
            </colgroup>
            <thead>
            <tr>
              <th>№</th>
              <th>xᵢ</th>
              <th>Приближённое yᵢ</th>
              <th>Точное y(xᵢ)</th>
            </tr>
            </thead>
            <tbody>
            {xRow.map((x, index) => (
                <tr key={index}>
                  <td className="number-cell">{index}</td>
                  <td className="number-cell">{formatNumber(x)}</td>
                  <td className="number-cell">{formatNumber(yRow[index])}</td>
                  <td className="number-cell">{formatNumber(exactSolution[index])}</td>
                </tr>
            ))}
            </tbody>
          </table>
        </div>
      </article>
  );
}