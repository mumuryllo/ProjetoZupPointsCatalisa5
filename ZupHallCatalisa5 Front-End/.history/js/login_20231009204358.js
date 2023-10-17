document.addEventListener('DOMContentLoaded', function () {
    const loginForm = document.querySelector('#loginForm');

    // Adicione um evento de 'submit' no formulário de login
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
            localStorage.setItem('token', data.token);

            // Use o token de autenticação para obter as informações do usuário
            const token = localStorage.getItem('token');

            fetch('http://localhost:8080/usuarios', {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`
                },
            })
            .then(response => response.json())
            .then(userList => {
                console.log(userList);

                // Obtenha a função (role) do usuário
                const userRole = userList[0].role;

                // Defina o valor selecionado no modal com base na função (role) do usuário
                document.querySelector('#cargoSelect').value = userRole;

                // Abra o modal após o login
                $('#cargoModal').modal('show');

                // Defina um evento de 'click' para o botão 'Confirmar' no modal
                document.querySelector('#confirmarCargo').addEventListener('click', function () {
                    const selectedCargo = document.querySelector('#cargoSelect').value;

                    // Lógica de redirecionamento com base na função (role) selecionada
                    if (selectedCargo === 'ROLE_COLABORADOR') {
                        window.location.href = 'colaborador.html';
                    } else if (selectedCargo === 'ROLE_GESTOR') {
                        window.location.href = 'gestor.html';
                    } else {
                        console.error('Função de usuário desconhecida:', selectedCargo);
                    }
                });
            })
            .catch(error => {
                console.error('Erro ao obter informações do usuário:', error);
            });
        })
        .catch(error => {
            console.error('Erro na autenticação:', error);
        });
    });
});





