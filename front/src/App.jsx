import { useState } from "react";
import InputForm from "./InputForm.jsx";
import SolutionPanel from "./SolutionPanel.jsx";
import ChartPanel from "./ChartPanel.jsx";
import { solveOde } from "./api.js";
import { validateForm } from "./validation.js";

const DEFAULT_FORM = {
  number: "1",
  method: "euler",
  y0: "",
  x0: "",
  xn: "",
  h: "",
  epsilon: ""
};

export default function App() {
  const [form, setForm] = useState(DEFAULT_FORM);
  const [solution, setSolution] = useState(null);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  function changeField(event) {
    const { name, value } = event.target;
    setForm(current => ({ ...current, [name]: value }));
  }

  async function submit(event) {
    event.preventDefault();

    const validationErrors = validateForm(form);

    if (validationErrors.length > 0) {
      setSolution(null);
      setError(validationErrors.join("\n"));
      return;
    }

    const payload = {
      number: Number(form.number),
      method: form.method,
      y0: Number(form.y0),
      x0: Number(form.x0),
      xn: Number(form.xn),
      h: Number(form.h),
      epsilon: Number(form.epsilon)
    };

    setLoading(true);
    setError("");
    setSolution(null);

    try {
      const result = await solveOde(payload);
      setSolution(result);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  }

  return (
    <main>
      <h1>Решение задачи Коши</h1>

      <InputForm form={form} loading={loading} changeField={changeField} submit={submit} />

      {error && (
        <section className="error-panel">
          <h2>Ошибка</h2>
          <pre>{error}</pre>
        </section>
      )}

      {solution && (
        <>
          <SolutionPanel solution={solution} equationNumber={Number(form.number)} />
          <ChartPanel solution={solution} equationNumber={Number(form.number)} />
        </>
      )}
    </main>
  );
}
