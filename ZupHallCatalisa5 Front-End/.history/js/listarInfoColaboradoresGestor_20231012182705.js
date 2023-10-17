document.addEventListener("DOMContentLoaded", function () {
    function fetchColaboradoresData() {
        const url = `http://localhost:8080/colaboradores`;
        const token = localStorage.getItem('token');

        fetch(url, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        })
        .then(response => {
            if (!response.ok) {
                console.error('Erro na requisição:', response.status, response.statusText);
                throw new Error(`Erro na requisição: ${response.status} - ${response.statusText}`);
            }
            return response.json();
        })
        .then(data => {
            const tbody = document.querySelector('tbody');
            tbody.innerHTML = ''; 

            data.forEach(colaborador => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${colaborador.nome}</td>
                    <td>${colaborador.username}</td>
                    <td>${colaborador.pontosAcumulados}</td>
                `;
                tbody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Erro na requisição:', error);
        });
    }


    function fetchGestorData() {
        const url = `http://localhost:8080/usuarios/${userId}`;

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
            const nomeGestor = document.getElementById('nomeGestor');
            const emailGestor = document.getElementById('emailGestor');
            
            nomeGestor.textContent = data.nome;
            emailGestor.textContent = data.email;
        })
        .catch(error => {
            console.error('Erro na requisição:', error);
        });
    }
    fetchColaboradoresData();
    fetchGestorData();
});