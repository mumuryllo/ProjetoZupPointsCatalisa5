document.addEventListener("DOMContentLoaded", function () {
  buscaTodosProdutos();
});

function buscaTodosProdutos() {
  const token = localStorage.getItem("token");
  const url = "http://localhost:8080/beneficios";

  fetch(url, {
    method: "GET",
    headers: {
      Authorization: `Bearer ${token}`,
    },
  })
    .then((data) => {
      return data.json();
    })
    .then((todosProdutos) => {
      let data1 = "";
      todosProdutos.forEach((values) => {
        data1 += `
                <tr>
                    <th scope="row">${values.id}</th>
                    <td>${values.nome}</td>
                    <td>${values.qtdPontosParaComprar}</td>
                    <td>${values.qtdDisponivel}</td>
                    <td>${values.valor}</td>
                </tr>
            `;
      });
      const tabela = document
        .getElementById("table-produtos")
        .querySelector("tbody");
      tabela.innerHTML = data1;
    })
    .catch((error) => {
      console.error("Ocorreu um erro durante a solicitação:", error);
    });
}
