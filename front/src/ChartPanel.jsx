import {
  Chart as ChartJS,
  Legend,
  LinearScale,
  LineElement,
  PointElement,
  Title,
  Tooltip
} from "chart.js";
import { Line } from "react-chartjs-2";
import { buildExactChartData } from "./exactSolutions.js";
import { METHOD_NAMES } from "./constants.js";

ChartJS.register(LinearScale, PointElement, LineElement, Title, Tooltip, Legend);

export default function ChartPanel({ solution, equationNumber }) {
  const exactData = buildExactChartData(equationNumber, solution.x0, solution.xn, solution.y0);
  const approxData = downsample(solution.table.x_row.map((x, index) => ({ x, y: solution.table.y_row[index] })), 900);
  const nodeData = downsample(solution.table.x_row.map((x, index) => ({ x, y: solution.table.y_row[index] })), 160);

  const data = {
    datasets: [
      {
        label: "Точное решение",
        data: exactData,
        borderColor: "#00695c",
        backgroundColor: "#00695c",
        borderWidth: 3,
        pointRadius: 0,
        tension: 0.12
      },
      {
        label: METHOD_NAMES[solution.method] ?? solution.method,
        data: approxData,
        borderColor: "#c62828",
        backgroundColor: "#c62828",
        borderWidth: 2,
        pointRadius: 0,
        tension: 0.12
      },
      {
        label: "Узлы приближённого решения",
        data: nodeData,
        borderColor: "#1565c0",
        backgroundColor: "#1565c0",
        showLine: false,
        pointRadius: 3,
        pointHoverRadius: 5
      }
    ]
  };

  const options = {
    responsive: true,
    maintainAspectRatio: false,
    parsing: false,
    plugins: {
      legend: {
        position: "top"
      },
      title: {
        display: true,
        text: "Точное решение и приближённое решение"
      }
    },
    scales: {
      x: {
        type: "linear",
        title: {
          display: true,
          text: "x"
        }
      },
      y: {
        title: {
          display: true,
          text: "y"
        }
      }
    }
  };

  return (
    <section>
      <h2>График</h2>
      <div className="chart-wrapper">
        <Line data={data} options={options} />
      </div>
    </section>
  );
}

function downsample(data, maxPoints) {
  if (data.length <= maxPoints) {
    return data;
  }

  const step = Math.ceil(data.length / maxPoints);
  return data.filter((_, index) => index % step === 0 || index === data.length - 1);
}
