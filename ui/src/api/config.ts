import type { CreateClientConfig } from "./generated/client";

export const createClientConfig: CreateClientConfig = (config) => ({
    ...config,
    fetch: (input, init = {}) => fetch(input, { ...init, credentials: 'include' }),
    baseUrl: 'http://localhost:8090/'
});