function enviarAvaliacao() {
    const qtdPontos = document.getElementById("pontos").value;
    const destinatarioEmail = document.getElementById("destinatarioEmail").value;
    const mensagem = document.getElementById("mensagem").value;

    const data = {
        qtdPontos,
        destinatarioEmail,
        mensagem
    };

    const url = "http://localhost:8080/avaliacoes";
    const token = localStorage.getItem('token'); 

    const requestOptions = {
        method: "POST",
        headers: {
            "Authorization": `Bearer ${token}`,
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    };

    fetch(url, requestOptions)
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw  Error("Erro na requisição.");
            }
        })
        .then(data => {
            alert("Avaliação enviada")
            window.location.href='colaborador.html';
            console.log("Requisição bem-sucedida: ", data);
        })
        .catch(error => {
            console.error("Erro: ", error);
        });
}

document.querySelector("form").addEventListener("submit", function (e) {
    e.preventDefault(); 
    enviarAvaliacao(); 
});

function listarAvaliacoes() {
    const url = "http://localhost:8080/avaliacoes"; // URL da requisição GET

    // Substitua o token apropriado ou adicione cabeçalhos necessários
    const token = localStorage.getItem('token');
    const requestOptions = {
        method: "GET",
        headers: {
            "Authorization": `Bearer ${token}`,
        },
    };

    fetch(url, requestOptions)
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error("Erro na requisição GET.");
            }
        })
        .then(data => {
            // Supondo que você tem uma tabela com ID "tabelaAvaliacoes" para exibir os resultados
            const tabela = document.getElementById("tabelaAvaliacoes");
            
            // Limpe a tabela removendo todas as linhas existentes
            while (tabela.rows.length > 1) {
                tabela.deleteRow(1);
            }

            data.forEach(avaliacao => {
                const newRow = tabela.insertRow();
                newRow.innerHTML = `
                    <td>${avaliacao.qtdPontos}</td>
                    <td>${avaliacao.dataDaAvaliacao}</td>
                    <td>${avaliacao.remetenteEmail}</td>
                    <td>${avaliacao.mensagem}</td>
                `;
            });
        })
        .catch(error => {
            console.error("Erro na listagem de avaliações: ", error);
        });
}

// Chame a função listarAvaliacoes ao carregar a página ou quando for apropriado
listarAvaliacoes();