const API_URL = "/api/v1/ode";

export async function solveOde(payload) {
  const response = await fetch(API_URL, {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(payload)
  });

  const data = await response.json();

  if (!response.ok || data.calculation_error) {
    throw new Error(extractErrorMessage(data));
  }

  return data;
}

function extractErrorMessage(data) {
  if (data.calculation_error) {
    return data.calculation_error;
  }

  if (data.errors) {
    return Object.entries(data.errors)
      .map(([field, messages]) => `${field}: ${messages.join(", ")}`)
      .join("\n");
  }

  return "Ошибка запроса.";
}
