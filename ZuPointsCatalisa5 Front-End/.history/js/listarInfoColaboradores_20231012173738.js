function fetchColaboradorData() {
    const colaboradorId = localStorage.getItem('id');
    const url = `http://localhost:8080/colaboradores/${colaboradorId}`;
    const token = localStorage.getItem; 

    fetch(url, {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`Erro na requisição: ${response.status} - ${response.statusText}`);
            }
            return response.json();
        })
        .then(data => {
            document.querySelector("td:contains('Nome do Colaborador') + td").textContent = data.nome;
            document.querySelector("td:contains('colaborador@example.com') + td").textContent = data.email;
            document.querySelector("td:contains('1000') + td").textContent = data.pontosDoacao;
            document.querySelector("td:contains('500') + td").textContent = data.pontosAcumulados;
        })
        .catch(error => {
            console.error('Erro na requisição:', error);
        });
}

fetchColaboradorData();