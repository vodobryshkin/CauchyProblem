import { BlockMath } from "react-katex";
import { EQUATION_OPTIONS, EQUATIONS, METHOD_OPTIONS } from "./constants.js";

export default function InputForm({ form, loading, changeField, submit }) {
  const equation = EQUATIONS[Number(form.number)];

  return (
    <form onSubmit={submit}>
      <fieldset>
        <h2>Исходные данные</h2>

        <label>
          ОДУ
          <select name="number" value={form.number} onChange={changeField}>
            {EQUATION_OPTIONS.map(option => (
              <option key={option.value} value={option.value}>{option.label}</option>
            ))}
          </select>
        </label>

        <label>
          Метод
          <select name="method" value={form.method} onChange={changeField}>
            {METHOD_OPTIONS.map(option => (
              <option key={option.value} value={option.value}>{option.label}</option>
            ))}
          </select>
        </label>

        <div className="formula-box">
          <BlockMath math={equation.odeLatex} />

          <div className="exact-formula-row">
            <span>Точное решение:</span>
            <BlockMath math={equation.exactLatex} />
          </div>
        </div>

        <div className="input-grid">
          <label>
            y₀
            <input name="y0" type="number" step="any" value={form.y0} onChange={changeField} />
          </label>

          <label>
            x₀
            <input name="x0" type="number" step="any" value={form.x0} onChange={changeField} />
          </label>

          <label>
            xₙ
            <input name="xn" type="number" step="any" value={form.xn} onChange={changeField} />
          </label>

          <label>
            h
            <input name="h" type="number" step="any" min="0.000000001" value={form.h} onChange={changeField} />
          </label>

          <label>
            ε
            <input name="epsilon" type="number" step="any" min="0.000000001" value={form.epsilon} onChange={changeField} />
          </label>
        </div>

        <button type="submit" disabled={loading}>{loading ? "Считаю..." : "Рассчитать"}</button>
      </fieldset>
    </form>
  );
}
