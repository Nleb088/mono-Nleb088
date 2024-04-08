export default function magicStones(stones: number[]): number[] {
    if (!stones) {
        return stones;
    }
    let stonesByValue: Set<number> = new Set();

    for (const stone of stones) {
        let finalValue = stone;
        while (stonesByValue.has(finalValue)) {
            stonesByValue.delete(finalValue);
            finalValue++;
        }
        stonesByValue.add(finalValue);
    }
    return Array.from(stonesByValue);
}