const formulario = document.querySelector("#modal-register form");
const Inome = document.getElementById("nome");
const Irm = document.getElementById("rm");
const Iresponsavel = document.getElementById("responsavel");
const Iemail = document.getElementById("email");
const Itelefone = document.getElementById("telefone");
const Ifoto = document.getElementById("imagem");

// Função para limpar os campos do formulário
const clearFormFields = () => {
    Inome.value = "";
    Irm.value = "";
    Iresponsavel.value = "";
    Iemail.value = "";
    Itelefone.value = "";
    Ifoto.value = "";
};

// Função para validar o nome (máximo de 60 caracteres)
const validarNome = (nome) => {
    if (nome.length > 60) {
        alert("O nome deve ter no máximo 60 caracteres.");
        return false;
    }
    return true;
};

// Função para validar a matrícula (apenas números, 5 caracteres)
const validarMatricula = (matricula) => {
    const matriculaRegex = /^[0-9]{5}$/;
    if (!matriculaRegex.test(matricula)) {
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
    const telefoneFormatado = formatarTelefone(telefone);
    if (telefone.replace(/\D/g, '').length !== 11) {
        alert("O telefone deve ter exatamente 11 dígitos.");
        return false;
    }
    Itelefone.value = telefoneFormatado; // Atualiza o valor do campo com o telefone formatado
    return true;
};

// Função para validar o e-mail (máximo de 150 caracteres)
const validarEmail = (email) => {
    if (email.length > 150) {
        alert("O e-mail deve ter no máximo 150 caracteres.");
        return false;
    }
    // Validação simples de formato de e-mail
    const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
    if (!emailRegex.test(email)) {
        alert("Por favor, insira um e-mail válido.");
        return false;
    }
    return true;
};

// Função para validar o responsável (máximo de 60 caracteres)
const validarResponsavel = (responsavel) => {
    if (responsavel.length > 60) {
        alert("O nome do responsável deve ter no máximo 60 caracteres.");
        return false;
    }
    return true;
};

// Adiciona evento de submit no formulário
formulario.addEventListener("submit", async (event) => {
    event.preventDefault(); // Impede o envio padrão do formulário

    // Valida os campos antes de enviar
    if (
        !validarNome(Inome.value) ||
        !validarMatricula(Irm.value) ||
        !validarTelefone(Itelefone.value) ||
        !validarEmail(Iemail.value) ||
        !validarResponsavel(Iresponsavel.value)
    ) {
        return; // Se algum campo for inválido, não envia o formulário
    }

    try {
        const formData = new FormData();

        // Adiciona os campos do formulário ao FormData
        formData.append("nome", Inome.value);
        formData.append("matricula", Irm.value);
        formData.append("responsavel", Iresponsavel.value);
        formData.append("email", Iemail.value);
        formData.append("telefone", Itelefone.value);

        // Adiciona o arquivo de imagem
        const file = Ifoto.files[0];
        if (file) {
            formData.append("file", file);
        }

        // Envia os dados do formulário (incluindo a imagem) para o servidor
        const response = await fetch("https://mystudenthub.azurewebsites.net/mshub/new", {
            method: "POST",
            body: formData,
        });

        if (!response.ok) {
            throw new Error("Erro ao enviar os dados do aluno.");
        }

        const result = await response.json();
        console.log("Aluno cadastrado com sucesso!", result);

        alert("Aluno cadastrado com sucesso!");
    } catch (error) {
        console.error("Erro:", error.message);
        alert("Erro ao cadastrar aluno. Por favor, tente novamente.");
    } finally {
        // Limpar os campos do formulário
        clearFormFields();
        // Fechar o modal
        modalRegister.close();
        window.location.reload(); // Atualiza a página
    }
});

// Evento de formatação automática do telefone
Itelefone.addEventListener("input", (e) => {
    console.log("Formatação chamada"); // Adiciona um log para depuração
    e.target.value = formatarTelefone(e.target.value);
});
