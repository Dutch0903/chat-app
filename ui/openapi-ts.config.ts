import { defineConfig } from "@hey-api/openapi-ts";

export default defineConfig({
  input: "http://localhost:8090/openapi",
  logs: {
    level: "info",
  },
  output: {
    clean: true,
    format: "prettier",
    list: "eslint",
    path: "./src/api/generated",
    tsConfigPath: "off",
  },
  plugins: [
    {
      name: "@hey-api/schemas",
      nameBuilder: (name) => `${name}Schema`,
      type: "json",
      exportFromIndex: true,
    },
    {
      name: "@hey-api/client-fetch",
      throwOnError: true,
      strictBaseUrl: true,
      // Relative to the hey-api output directory
      runtimeConfigPath: '../config',
    },
    {
      name: "@hey-api/sdk",
      exportFromIndex: true,
    },
    {
      name: "@hey-api/typescript",
      case: "PascalCase",
      enums: "javascript",
    },
  ],
});
