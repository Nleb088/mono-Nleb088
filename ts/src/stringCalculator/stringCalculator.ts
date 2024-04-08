import ErrorMessages from "./stringCalculatorErrors.enum";

export default class StringCalculator {
    static Add(input: string): number {
        const optionalDelimiter: string = this.extractOptionalDelimiter(input);
        const startIndex: number = optionalDelimiter ? input.indexOf('\n') + 1 : 0;
        const numbers: string = input.substring(startIndex);
        const delimiters = new RegExp(`[${optionalDelimiter},\n]`);

        const sum: number = numbers.split(delimiters).reduce((acc: number, number: string) => {
            const num = parseInt(number);
            if (num < 0) {
                throw new Error(ErrorMessages.NEGATIVE_NUMBER_ERROR);
            }
            if (isNaN(num) || num > 1000) {
                return acc;
            }
            return acc + num;
        }, 0);

        return sum;
    }

    private static extractOptionalDelimiter(input: string): string {
        if (input.startsWith("//") && input.indexOf("\n") === 3) {
            return input[2];
        }
        return "";
    }
}