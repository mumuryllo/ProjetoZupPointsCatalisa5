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
            .then(data => {
                const beneficiosTable = document.querySelector('#beneficiosModal table tbody');
                beneficiosTable.innerHTML = ''; // Limpa a tabela

                data.forEach(beneficio => {
                    const row = beneficiosTable.insertRow();
                    row.innerHTML = `
                        <td>${beneficio.nome}</td>
                        <td>${beneficio.valor}</td>
                        <td>${beneficio.voucher}</td>
                        <td>${beneficio.dataCriacao}</td>
                    `;
                });
            })
            .catch(error => {
                console.error('Erro na requisição dos benefícios:', error);
            });
    }

    // Adiciona um ouvinte de evento para o botão "Seus Benefícios" que chama a função fetchBeneficiosData.
    const beneficiosButton = document.querySelector('#beneficiosButton');
    beneficiosButton.addEventListener('click', fetchBeneficiosData);

    fetchColaboradorData();
});