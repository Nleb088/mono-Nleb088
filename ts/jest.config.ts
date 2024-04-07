import type { Config } from 'jest';

const config: Config = {
    preset: 'ts-jest',
    testEnvironment: 'node',
    roots: ['./src/'],
    testMatch: ['**/*.test.ts'],
    moduleDirectories: ["node_modules", "src"]
};

export default config;