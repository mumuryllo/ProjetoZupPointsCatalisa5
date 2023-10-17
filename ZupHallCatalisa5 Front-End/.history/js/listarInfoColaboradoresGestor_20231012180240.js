document.addEventListener("DOMContentLoaded", function () {
    function fetchColaboradorData() {
        const url = `http://localhost:8080/colaboradores/${colaboradorId}`;
        const token =  localStorage.getItem('token'); // Substitua pelo seu token Bearer válido

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
                document.getElementById('nomeColaborador').textContent = data.nome;
                document.getElementById('emailColaborador').textContent = data.username;
                document.getElementById('pontosDoacao').textContent = data.pontosDoacao;
                document.getElementById('pontosAcumulados').textContent = data.pontosAcumulados;
            })
            .catch(error => {
                console.error('Erro na requisição:', error);
            });
    }

    fetchColaboradorData();
});