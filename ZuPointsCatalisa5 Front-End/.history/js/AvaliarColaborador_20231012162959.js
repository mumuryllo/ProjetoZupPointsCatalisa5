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
                throw new Error("Erro na requisição.");
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