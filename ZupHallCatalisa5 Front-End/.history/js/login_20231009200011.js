document.addEventListener('DOMContentLoaded', function () {
    const loginForm = document.querySelector('#loginForm');

    loginForm.addEventListener('submit', function (e) {
        e.preventDefault();

        const usuario = document.querySelector('#usuario').value;
        const senha = document.querySelector('#senha').value;

        const loginData = {
            username: usuario,
            password: senha,
        };

        fetch('http://localhost:8080/login/auth', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(loginData),
        })
        .then(response => response.json())
        .then(data => {
            console.log('Token de autenticação:', data.token);

            // Defina a função do usuário diretamente aqui
            const userRole = 'ROLE_ COLABORADOR'; // Substitua isso pelo seu valor de ROLE

            if (data.token) {
                // Adicione o token de autorização ao cabeçalho da solicitação
                const headers = {
                    'Authorization': `Bearer ${data.token}`,
                    'Content-Type': 'application/json',
                };

                // Exemplo de requisição com o token de autorização
                fetch('http://localhost:8080/usuarios', {
                    method: 'GET',
                    headers: headers,
                })
                .then(response => response.json())
                .then(userInfo => {
                    console.log(userInfo);

                    if (  userRole === 'ROLE_COLABORADOR') {
                        window.location.href = 'colaborador.html';
                    } else if (userInfo.role === 'ROLE_GESTOR' && userRole === 'ROLE_GESTOR') {
                        window.location.href = 'gestor.html';
                    } else {
                        console.error('Função de usuário desconhecida ou incompatível:', userInfo.role, userRole);
                    }
                })
                .catch(error => {
                    console.error('Erro na requisição:', error);
                });
            } else {
                console.error('Token de autenticação ausente ou inválido');
            }
        })
        .catch(error => {
            console.error('Erro na autenticação:', error);
        });
    });
});