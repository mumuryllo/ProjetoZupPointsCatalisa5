document.addEventListener('DOMContentLoaded', function () {
    buscaTodosProdutos(); 
});

function buscaTodosProdutos() {
    const token = localStorage.getItem('token');
    const url = "http://localhost:8080/beneficios";

    fetch(url, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}` 
        }
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
                    <td><button class="btn btn-primary" onclick="resgatarBeneficio(${values.id})">Resgatar</button></td> <!-- Botão de resgate -->
                </tr>
            `;
            });
            const tabela = document.getElementById("table-produtos").querySelector("tbody");
            tabela.innerHTML = data1;
        })
        .catch((error) => {
            console.error("Ocorreu um erro durante a solicitação:", error);
        });
}

function resgatarBeneficio(id) {
    const token = localStorage.getItem('token');
    const resgateUrl = `http://localhost:8080/beneficios/resgatar`;

    // Dados para o corpo da requisição
    const data = {
        id: id
    };

    fetch(resgateUrl, {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then((response) => {
            if (response.ok) {
                console.log(`Benefício com ID ${id} foi resgatado com sucesso.`);
            } else {
                throw new Error('Falha na requisição de resgate');
            }
        })
        .catch((error) => {
            console.error("Ocorreu um erro durante a solicitação de resgate:", error);
        });
}

