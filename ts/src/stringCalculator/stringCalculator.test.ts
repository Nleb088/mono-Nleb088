import StringCalculator from "./stringCalculator";

test('should return 0', () => {
    expect(StringCalculator.Add("")).toStrictEqual(0);
});
test('should return 1', () => {
    expect(StringCalculator.Add("1")).toStrictEqual(1);
});
test('should return 3', () => {
    expect(StringCalculator.Add("1,2")).toStrictEqual(3);
});
