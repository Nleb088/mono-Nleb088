import { Todo } from "./models/todo.model";

export default class Model {
    todos: Todo[] = [];

    constructor() {
        this.todos = [
            { id: 1, text: 'Run a marathon', complete: false },
            { id: 2, text: 'Plant a garden', complete: false },
        ];
    }

    addTodo(todoText: string): void {
        const todo = {
            id: this.todos.length > 0 ? this.todos[this.todos.length - 1].id + 1 : 1,
            text: todoText,
            complete: false,
        };
        this.todos.push(todo);
    }

    // Map through all todos, and replace the text of the todo with the specified id
    editTodo(id: number, updatedText: string) {
        this.todos = this.todos.map((todo) =>
            todo.id === id ? { id: todo.id, text: updatedText, complete: todo.complete } : todo,
        );
    }

    // Filter a todo out of the array by id
    deleteTodo(id: number) {
        this.todos = this.todos.filter((todo) => todo.id !== id);
    }

    // Flip the complete boolean on the specified todo
    toggleTodo(id: number) {
        this.todos = this.todos.map((todo) =>
            todo.id === id ? { id: todo.id, text: todo.text, complete: !todo.complete } : todo,
        );
    }
}