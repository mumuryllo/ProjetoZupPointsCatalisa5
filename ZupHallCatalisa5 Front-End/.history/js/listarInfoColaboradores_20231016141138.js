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

    // Função para preencher os benefícios
    function fillBeneficiosTable(beneficios) {
        const beneficiosTable = document.querySelector('#beneficiosModal table tbody');
        beneficiosTable.innerHTML = ''; // Limpa a tabela

        beneficios.forEach(beneficio => {
            const row = beneficiosTable.insertRow();
            row.innerHTML = `
                <td>${beneficio.nome}</td>
                <td>${beneficio.valor}</td>
                <td>${beneficio.voucher}</td>
                <td>${beneficio.dataCriacao}</td>
            `;
        });
    }

    // Adiciona um ouvinte de evento para o botão "Seus Benefícios" que preenche a tabela de benefícios.
    const beneficiosButton = document.querySelector('#beneficiosButton');
    beneficiosButton.addEventListener('click', function () {
        const colaboradorId = localStorage.getItem('id');
        const url = `http://localhost:8080/colaboradores/${colaboradorId}/beneficios`;
        const token = localStorage.getItem('token');

        fetch(url, {
            headers: {
                'Authorization': `Bearer ${token}`
            }),
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Erro na requisição dos benefícios: ${response.status} - ${response.statusText}`);
                }
                return response.json();
            })
            .then(data => {
                fillBeneficiosTable(data);
            })
            .catch(error => {
                console.error('Erro na requisição dos benefícios:', error);
            });
    });

    fetchColaboradorData();
});