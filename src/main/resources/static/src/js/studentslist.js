const apiurlget = 'https://mystudenthub.azurewebsites.net/mshub/get';  // URL da API para pegar os dados
const apiurlPut = 'https://mystudenthub.azurewebsites.net/mshub/update';  // URL da API para pegar os dados
const apiurlDell = 'https://mystudenthub.azurewebsites.net/mshub/delete';  // URL da API para pegar os dados
const apiurlimgPut = 'https://mystudenthub.azurewebsites.net/image/updt';  // URL da API para pegar os dados
const apiurlimgDell = 'https://mystudenthub.azurewebsites.net/image/del';  // URL da API para pegar os dados
const apiurlimgNew = 'https://mystudenthub.azurewebsites.net/image/upload';  // URL da API para pegar os dados

// Função para carregar os dados assim que a página for carregada
window.onload = function() {
    main();
};

// Função principal que busca os dados da API e os exibe
async function main() {
    try {
        // Fetch para pegar os dados da API
        const response = await fetch(apiurlget);
        
        // Verifica se a resposta é bem-sucedida (status 200)
        if (!response.ok) {
            console.error('Erro ao buscar os dados:', response.status);
            return;
        }
        
        // Converte a resposta em JSON
        const dados = await response.json();

        // Verifica se os dados são válidos
        console.log(dados); // Verifique o que está sendo retornado pela API
        renderAlunos(dados);
    } catch (error) {
        console.error('Erro ao fazer a requisição:', error);
    }
}

// Função que renderiza os dados dos alunos na tabela

function renderAlunos(dados) {
    const tbody = document.getElementById('studentTableBody');
    tbody.innerHTML = ''; // Limpa a tabela antes de inserir os dados
    
    // Loop para preencher cada linha da tabela com os dados dos alunos
    dados.forEach(aluno => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>
            <button type="button" id="alunoModal" style="all: unset; cursor: pointer" onclick="abrirModal('${aluno.id}')">
                <img src="${aluno.imagURL || 'src/img/icon-default.png'}" alt="Foto" width="42" height="42" style="border-radius: 180px">
            </button>   
            </td>
            <td>
                <button type="button" class="bt" style="all: unset; cursor: pointer" onclick="abrirModal('${aluno.id}')">
                    ${aluno.nome}
                </button>
            </td>
            <td>${aluno.email}</td>
            <td>${aluno.telefone}</td>
            <td>${aluno.matricula}</td>
            <td>${aluno.responsavel}</td>
            <td>
                <button class="b1-EditStudent" onclick="editarAluno('${aluno.id}')" style="all: unset; cursor: pointer" type="button">
                <img src="src/img/editar.png" alt="editar"  >
                </button>
                <button onclick="deletarAluno('${aluno.id}')" style="all: unset; cursor: pointer" type="button" >
                 <img src="src/img/excluir (1).PNG" alt="excluir"  >
                </button>
            </td>`;
        
        tbody.appendChild(tr);
    });
}

// Função para abrir o modal e buscar os dados do aluno

async function abrirModal(id) {
    try {
        const modal = document.getElementById("alunoModalInfo");
        if (!(modal instanceof HTMLDialogElement)) {
            throw new Error("Elemento 'alunoModal' não é um <dialog> válido.");
        }

        const detalhesContainer = document.getElementById("alunoDetalhes");

        // Buscar os dados do aluno pela API
        const response = await fetch(`${apiurlget}/${id}`);
        if (!response.ok) {
            console.error('Erro ao buscar os dados do aluno:', response.status);
            return;
        }

        const aluno = await response.json();

        // Preencher os detalhes do aluno no modal
        detalhesContainer.innerHTML = `
        <div class="aluno-photo-container">
            <img 
                src="${aluno.imagURL || 'src/img/icon-default.png'}" 
                alt="Foto do aluno" 
                class="aluno-photo">
        </div>
        
        <div class="aluno-info">
            <p class="aluno-detalhe aluno-id">
                <strong class="aluno-label">ID:</strong> ${aluno.id}
            </p>
            <p class="aluno-detalhe aluno-nome">
                <strong class="aluno-label">Nome:</strong> ${aluno.nome}
            </p>
            <p class="aluno-detalhe aluno-email">
                <strong class="aluno-label">Email:</strong> ${aluno.email}
            </p>
            <p class="aluno-detalhe aluno-telefone">
                <strong class="aluno-label">Telefone:</strong> ${aluno.telefone}
            </p>
            <p class="aluno-detalhe aluno-matricula">
                <strong class="aluno-label">Matrícula:</strong> ${aluno.matricula}
            </p>
            <p class="aluno-detalhe aluno-responsavel">
                <strong class="aluno-label">Responsável:</strong> ${aluno.responsavel}
            </p>
        </div>
        
        <div class="aluno-actions">
            <button class="b1-EditStudent aluno-btn-edit" onclick="editarAluno('${aluno.id}')" type="button">
                <img src="src/img/Action 2.png" alt="editar" class="aluno-btn-icon">
            </button>
            <button class="b1-DeleteStudent aluno-btn-delete" onclick="deletarAluno('${aluno.id}')" type="button">
                <img src="src/img/Action 3.png" alt="excluir" class="aluno-btn-icon">
            </button>
        </div>
    `;
    

        // Mostrar o modal
        modal.showModal();

    } catch (error) {
        console.error('Erro ao fazer a requisição para o modal:', error);
    }
}


// Adicionar evento para fechar o modal
document.getElementById("closeModal").addEventListener("click", () => {
    const modal = document.getElementById("alunoModalInfo");
    modal.close();
});

    

// Função para validar o nome (máximo de 60 caracteres)
const validarNome = (nome) => {
    if (nome && nome.length > 60) {  // Valida somente se o campo não estiver vazio
        alert("O nome deve ter no máximo 60 caracteres.");
        return false;
    }
    return true;
};

// Função para validar a matrícula (apenas números, 5 caracteres)
const validarMatricula = (matricula) => {
    if (matricula && !/^[0-9]{5}$/.test(matricula)) {  // Valida somente se o campo não estiver vazio
        alert("A matrícula deve conter apenas 5 números.");
        return false;
    }
    return true;
};

// Função para formatar o telefone durante a digitação
const formatarTelefone = (telefone) => {
    telefone = telefone.replace(/\D/g, ''); // Remove caracteres não numéricos
    if (telefone.length <= 2) {
        return `(${telefone}`;
    } else if (telefone.length <= 6) {
        return `(${telefone.substring(0, 2)}) ${telefone.substring(2)}`;
    } else {
        return `(${telefone.substring(0, 2)}) ${telefone.substring(2, 7)}-${telefone.substring(7, 11)}`;
    }
};

// Função para validar o telefone (máximo de 11 dígitos)
const validarTelefone = (telefone) => {
    if (telefone && telefone.replace(/\D/g, '').length !== 11) {  // Valida somente se o campo não estiver vazio
        alert("O telefone deve ter exatamente 11 dígitos.");
        return false;
    }
    return true;
};

// Função para validar o e-mail (máximo de 150 caracteres)
const validarEmail = (email) => {
    if (email && email.length > 150) {  // Valida somente se o campo não estiver vazio
        alert("O e-mail deve ter no máximo 150 caracteres.");
        return false;
    }
    // Validação simples de formato de e-mail
    const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
    if (email && !emailRegex.test(email)) {  // Só valida se o e-mail não estiver vazio
        alert("Por favor, insira um e-mail válido.");
        return false;
    }
    return true;
};

// Função para validar o responsável (máximo de 60 caracteres)
const validarResponsavel = (responsavel) => {
    if (responsavel && responsavel.length > 60) {  // Valida somente se o campo não estiver vazio
        alert("O nome do responsável deve ter no máximo 60 caracteres.");
        return false;
    }
    return true;
};

async function editarAluno(id) {
    const modalEdit = document.getElementById("modal-Edit-Student");
    const modalConfirm = document.getElementById("modal-confirm");
    const closeModalButton = modalEdit.querySelector("#close-edit-modal");
    const editForm = document.getElementById("editForm");

    // Abre o modal de edição
    modalEdit.showModal();

    // Fecha o modal de edição ao clicar no "X"
    closeModalButton.addEventListener("click", () => {
        modalEdit.close();
    });

    // Evento de submit do formulário
    editForm.addEventListener("submit", (event) => {
        event.preventDefault();  // Impede o envio tradicional do formulário

        // Pega os dados do formulário
        const Inome = document.getElementById("edit-nome").value.trim();
        const Itelefone = document.getElementById("edit-telefone").value.trim();
        const Imatricula = document.getElementById("edit-rm").value.trim();
        const Iemail = document.getElementById("edit-email").value.trim();
        const Iresponsavel = document.getElementById("edit-responsavel").value.trim();

        // Valida os campos obrigatórios
        if (
            !validarNome(Inome) ||
            !validarMatricula(Imatricula) ||
            !validarTelefone(Itelefone) ||
            !validarResponsavel(Iresponsavel)
        ) {
            return; // Se algum campo for inválido, não envia o formulário
        }

        // Se o campo de e-mail não foi alterado, não valida o e-mail
        if (Iemail && !validarEmail(Iemail)) {
            return; // Se o e-mail for inválido, não envia o formulário
        }

        // Exibe o modal de confirmação
        modalConfirm.showModal();
    });

    // Ações para o modal de confirmação
    const confirmYes = document.getElementById("confirm-yes");
    const confirmNo = document.getElementById("confirm-no");

    // Ao clicar em "Sim", envia os dados para o servidor
    confirmYes.addEventListener("click", async () => {
        try {
            // Pega os dados do formulário
            const Inome = document.getElementById("edit-nome").value.trim();
            const Itelefone = document.getElementById("edit-telefone").value.trim();
            const Imatricula = document.getElementById("edit-rm").value.trim();
            const Iemail = document.getElementById("edit-email").value.trim();
            const Iresponsavel = document.getElementById("edit-responsavel").value.trim();

            const alunoData = {
                nome: Inome || undefined,
                telefone: Itelefone || undefined,
                matricula: Imatricula || undefined,
                email: Iemail || undefined,
                responsavel: Iresponsavel || undefined
            };

            // Primeiro fetch para enviar os dados em formato JSON (sem imagem)
            const responseJson = await fetch(`${apiurlPut}/${id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(alunoData)
            });

            if (!responseJson.ok) {
                console.error("Erro ao atualizar os dados do aluno (JSON).");
                return;
            }

            // Espera 500ms antes de enviar a imagem
            setTimeout(async () => {
                const file = document.getElementById("edit-imagem").files[0];  // Arquivo de imagem
                if (file) {
                    const formData = new FormData();
                    formData.append("file", file);

                    const responseImage = await fetch(`${apiurlimgPut}/${id}`, {
                        method: 'PUT',
                        body: formData
                    });

                    if (responseImage.ok) {
                        console.log("Imagem atualizada com sucesso!");
                    } else {
                        console.error("Erro ao atualizar imagem.");
                    }
                }
            }, 1000); // Atraso de 1000ms para o segundo fetch
            modalConfirm.close();
            modalEdit.close();  // Fecha ambos os modais
            setTimeout(() => {
                window.location.reload();
            }, 1500); 
        } catch (error) {
            console.error("Erro ao enviar dados:", error);
            modalConfirm.close();
        }
    });

    // Caso o usuário clique em "Não", apenas fecha o modal de confirmação
    confirmNo.addEventListener("click", () => {
        modalConfirm.close();
        modalEdit.showModal();  // Reabre o modal de edição
    });
}

// Evento de formatação automática do telefone no modal de edição
document.getElementById("edit-telefone").addEventListener("input", (e) => {
    e.target.value = formatarTelefone(e.target.value);
});





async function deletarAluno(id) {
    const modalConfirm = document.getElementById("modal-confirm-delete");
    const confirmYes = document.getElementById("confirm-yes-delete");
    const confirmNo = document.getElementById("confirm-no-delete");

    // Exibe o modal de confirmação
    modalConfirm.showModal();

    // Caso o usuário clique em "Sim"
    confirmYes.addEventListener("click", async () => {
        try {
            // Primeiro fetch: Deleta a imagem do aluno
            const responseImg = await fetch(`${apiurlimgDell}/${id}`, {
                method: 'DELETE',
            });

            if (!responseImg.ok) {
                console.error("Erro ao deletar a imagem do aluno.");
                modalConfirm.close();
                return;
            }

            console.log("Imagem deletada com sucesso!");

            // Espera 500ms antes de fazer o segundo fetch
            setTimeout(async () => {
                // Segundo fetch: Deleta os dados do aluno
                const responseAluno = await fetch(`${apiurlDell}/${id}`, {
                    method: 'DELETE',
                });

                if (responseAluno.ok) {
                    console.log("Aluno deletado com sucesso!");
                } else {
                    console.error("Erro ao deletar o aluno.");
                }
            }, 500); // Atraso de 500ms
        } catch (error) {
            console.error("Erro ao deletar o aluno ou imagem:", error);
        } finally {
            modalConfirm.close(); // Fecha o modal de confirmação
            setTimeout(() => {
                window.location.reload();
            }, 1500); //
        }
    });

    // Caso o usuário clique em "Não"
    confirmNo.addEventListener("click", () => {
        modalConfirm.close(); // Apenas fecha o modal de confirmação
    });
}

async function buscarAluno() {
    const nome = document.getElementById('nome-valor').value.trim(); // Captura o valor do input
    const tbody = document.getElementById('studentTableBody'); // Corpo da tabela onde os dados são exibidos
    const quant = document.getElementById('quant'); // Contador de resultados

    // Limpa a tabela e o contador de resultados antes da busca
    tbody.innerHTML = '';
    quant.textContent = '';

    if (!nome) {
        quant.textContent = 'Digite um nome para buscar.';
        return;
    }

    try {
        // Chamada para a API de busca
        const response = await fetch(`https://mystudenthub.azurewebsites.net/mshub/get/buscar?nome=${nome}`);
        if (!response.ok) throw new Error('Erro na requisição');

        const dados = await response.json();

        if (dados.length === 0) {
            quant.textContent = 'Nenhum aluno encontrado.';
            return;
        }

        // Atualiza o contador com a quantidade de resultados encontrados
        quant.textContent = `Resultados encontrados: ${dados.length}`;

        // Renderiza os dados encontrados na tabela
        renderAlunos(dados);
    } catch (error) {
        console.error('Erro ao buscar alunos:', error);
        quant.textContent = 'Erro ao buscar alunos. Tente novamente.';
    }
}
function resetarPesquisa() {
    document.getElementById('nome-valor').value = ''; // Limpa o input
    document.getElementById('quant').textContent = ''; // Limpa o contador
    main(); // Recarrega todos os alunos
    setTimeout(() => {
        window.location.reload();
    }, 500); //
}

fetch('https://mystudenthub.azurewebsites.net/mshub/get/count')
  .then(response => response.text())  // Caso a resposta seja apenas texto
  .then(data => {
    console.log('Dados recebidos:', data);
    
    // Processa os dados recebidos
    const alunos = processarDados(data);
    console.log('Quantidade de alunos:', alunos);

    // Exibe a quantidade de alunos em um elemento HTML com id "alunos-count"
    document.getElementById("quant").innerHTML = `Quantidade: ${alunos}`;
  })
  .catch(error => {
    console.error('Erro:', error);
  });

// Função para processar os dados
function processarDados(dados) {
  // Neste caso, estamos assumindo que o valor de `data` é uma string que representa um número
  return dados; // Retorna diretamente o valor de `data` caso seja uma string com o número de alunos
}

