export default class StringCalculator {
    static Add(input: string): number {

        const sum: number = input.split(/[,\n]/).reduce((acc: number, number: string) => acc + (parseInt(number) || 0), 0);

        return sum;
    }
}