document.addEventListener("DOMContentLoaded", function () {
    function fetchColaboradorData() {
        const colaboradorId = localStorage.getItem('id');
        const url = `http://localhost:8080/colaboradores/${colaboradorId}`;
        const token = localStorage.getItem('token');

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

    function fetchBeneficiosData() {
        const colaboradorId = localStorage.getItem('id');
        const url = `http://localhost:8080/colaboradores/${colaboradorId}`;
        const token = localStorage.getItem('token');

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
            eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdGVsbGFAZ21haWwuY29tIiwiaWF0IjoxNjk3NDc2MDc2LCJleHAiOjE2OTc0Nzk2NzYsInJvbGUiOiJHRVNUT1IifQ.D5Y_d2K_CHLLn-fZsnVUzELTCecraWFi6qCX3uUENkk

    // Adiciona um ouvinte de evento para o botão "Seus Benefícios" que chama a função fetchBeneficiosData.
    const beneficiosButton = document.querySelector('#beneficiosButton');
    beneficiosButton.addEventListener('click', fetchBeneficiosData);

    fetchColaboradorData();
});