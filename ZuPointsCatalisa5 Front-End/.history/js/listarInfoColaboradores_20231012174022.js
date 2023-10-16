function fetchColaboradorData() {
    const colaboradorId = localStorage.getItem('id');
    const url = `http://localhost:8080/colaboradores/${colaboradorId}`;
    const token = 'seu_token_bearer_aqui'; // Substitua pelo seu token Bearer válido

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
            // Substitua os IDs dos elementos abaixo pelos IDs corretos em seu HTML
            document.getElementById('nomeColaborador').textContent = data.nome;
            document.getElementById('emailColaborador').textContent = data.email;
            document.getElementById('pontosDoacao').textContent = data.pontosDoacao;
            document.getElementById('pontosAcumulados').textContent = data.pontosAcumulados;
        })
        .catch(error => {
            console.error('Erro na requisição:', error);
        });
}

fetchColaboradorData();