export default class View {
    app: HTMLElement | null;
    title: HTMLTitleElement | null;
    form: HTMLFormElement | null;
    input: HTMLInputElement | null;
    submitButton: HTMLButtonElement | null;
    todoList: HTMLUListElement | null;

    constructor() {
        // The root element
        this.app = this.getElement('#root');

        // The title of the app
        this.title = document.c('h1');
        this.title.textContent = 'Todos';

        // The form, with a [type="text"] input, and a submit button
        this.form = this.createElement('form');

        this.input = this.createElement('input');
        this.input.type = 'text';
        this.input.placeholder = 'Add todo';
        this.input.name = 'todo';

        this.submitButton = this.createElement('button');
        this.submitButton.textContent = 'Submit';

        // The visual representation of the todo list
        this.todoList = this.createElement('ul', 'todo-list');

        // Append the input and submit button to the form
        this.form.append(this.input, this.submitButton);

        // Append the title, form, and todo list to the app
        this.app.append(this.title, this.form, this.todoList);
    }
}