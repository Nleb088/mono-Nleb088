import StringCalculator from "./stringCalculator";
import ErrorMessages from "./stringCalculatorErrors.enum";

test('should return 0', () => {
    expect(StringCalculator.Add("")).toStrictEqual(0);
});
test('should return 1', () => {
    expect(StringCalculator.Add("1")).toStrictEqual(1);
});
test('should return 3 with , delimiter', () => {
    expect(StringCalculator.Add("1,2")).toStrictEqual(3);
});
test('should return 15', () => {
    expect(StringCalculator.Add("1,2,3,4,5")).toStrictEqual(15);
});
test('should return 6 with \\n delimiter', () => {
    expect(StringCalculator.Add("1,2\n3")).toStrictEqual(6);
});
test('should return 6 with a custom delimiter', () => {
    expect(StringCalculator.Add("//;\n1,2;3")).toStrictEqual(6);
});
test('should throw an error as strings contains negative numbers', () => {
    expect(StringCalculator.Add("-5,2,-10,9")).toThrow(ErrorMessages.NEGATIVE_NUMBER_ERROR);
});
test('should ignore numbers superior to 1000', () => {
    expect(StringCalculator.Add("5,10,1664")).toStrictEqual(15);
});
