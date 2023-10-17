document.addEventListener("DOMContentLoaded", function () {
    function fetchColaboradoresData() {
        const url = `http://localhost:8080/colaboradores`;
        const token = localStorage.getItem('token');

        fetch(url, {
            headers: {
                'Authorization': `Bearer ${token}`
            }),
            .then(response => {
                if (!response.ok) {
                    console.error('Erro na requisição:', response.status, response.statusText);
                    throw new Error(`Erro na requisição: ${response.status} - ${response.statusText}`);
                }
                return response.json();
            })
            .then(data => {
                const tbody = document.querySelector('tbody');
                tbody.innerHTML = ''; // Limpa qualquer conteúdo existente

                data.forEach(colaborador => {
                    const row = document.createElement('tr');
                    row.innerHTML = `
                        <td>${colaborador.nome}</td>
                        <td>${colaborador.email}</td>
                        <td>${colaborador.pontosAcumulados}</td>
                    `;
                    tbody.appendChild(row);
                });
            })
            .catch(error => {
                console.error('Erro na requisição:', error);
            });
    }

    fetchColaboradoresData();
});