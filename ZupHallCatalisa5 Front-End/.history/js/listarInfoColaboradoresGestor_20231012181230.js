document.addEventListener("DOMContentLoaded", function () {
    function fetchColaboradorData() {
        const url = `http://localhost:8080/colaboradores`;
        const token = localStorage.getItem('token');
        const colaboradorId = localStorage.getItem('id');

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
                // Encontre o colaborador desejado com base no seu ID
                const colaborador = data.find(colaborador => colaborador.id === colaboradorId);

           
                    document.getElementById('nomeColaborador').textContent = colaborador.nome;
                    document.getElementById('emailColaborador').textContent = colaborador.email;
                    document.getElementById('pontosAcumulados').textContent = colaborador.pontosAcumulados;
             
                
            })
            .catch(error => {
                console.error('Erro na requisição:', error);
            });
    }

    fetchColaboradorData();
});
