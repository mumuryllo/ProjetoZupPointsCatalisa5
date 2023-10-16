// Função para enviar a avaliação
function enviarAvaliacao() {
    // Obter os valores dos campos do formulário
    const qtdPontos = document.getElementById("pontos").value;
    const destinatarioEmail = document.getElementById("destinatarioEmail").value;
    const mensagem = document.getElementById("mensagem").value;

    const data = {
        qtdPontos,
        destinatarioEmail,
        mensagem
    };

    const url = "http://localhost:8080/avaliacoes";
    const token = localStorage.getItem('token'); // Certifique-se de que está buscando a chave correta

    const requestOptions = {
        method: "POST",
        headers: {
            "Authorization": `Bearer ${token}`,
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    };

    // Realizar a requisição POST
    fetch(url, requestOptions)
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error("Erro na requisição.");
            }
        })
        .then(data => {
            console.log("Requisição bem-sucedida: ", data);
        })
        .catch(error => {
            console.error("Erro: ", error);
        });
}

// Adicione um evento de clique ao botão "Enviar"
document.querySelector("form").addEventListener("submit", function (e) {
    e.preventDefault(); // Impede que o formulário seja enviado normalmente
    enviarAvaliacao(); 
});