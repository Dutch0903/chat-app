/**
 * Configuration for a registered message type
 */
type MessageConfig<
  T,
  P extends Record<string, string> = Record<string, never>,
> = {
  topic: P extends Record<string, never> ? string : (params: P) => string;
  factory: (data: Record<string, unknown>) => T;
};

/**
 * Typed message constant that carries parameter information
 */
export type MessageTypeToken<
  T,
  P extends Record<string, string> = Record<string, never>,
> = {
  readonly __type: T;
  readonly __params: P;
  readonly __messageType: string;
};

/**
 * Self-registering message registry
 * Messages register themselves and receive a typed token back
 */
class MessageRegistryClass {
  private registry = new Map<
    string,
    MessageConfig<unknown, Record<string, string>>
  >();

  /**
   * Register a message type and return a typed token
   */
  register<T, P extends Record<string, string> = Record<string, never>>(
    messageType: string,
    config: MessageConfig<T, P>,
  ): MessageTypeToken<T, P> {
    if (this.registry.has(messageType)) {
      console.warn(
        `Message type "${messageType}" is already registered. Overwriting.`,
      );
    }
    this.registry.set(
      messageType,
      config as MessageConfig<unknown, Record<string, string>>,
    );

    // Return a typed token
    return {
      __type: undefined as unknown as T,
      __params: undefined as unknown as P,
      __messageType: messageType,
    };
  }

  /**
   * Get the topic for a message type token
   */
  getTopic<P extends Record<string, string>>(
    messageType: string,
    params?: P,
  ): string | null {
    const config = this.registry.get(messageType);
    if (!config) {
      console.error(`Message type "${messageType}" is not registered`);
      return null;
    }

    if (typeof config.topic === "function") {
      return config.topic(params || {});
    }
    return config.topic;
  }

  /**
   * Get the factory for a message type token
   */
  getFactory<T>(
    messageType: string,
  ): ((data: Record<string, unknown>) => T) | null {
    const config = this.registry.get(messageType);
    if (!config) {
      console.error(`Message type "${messageType}" is not registered`);
      return null;
    }
    return config.factory as (data: Record<string, unknown>) => T;
  }

  /**
   * Check if a message type is registered
   */
  isRegistered(messageType: string): boolean {
    return this.registry.has(messageType);
  }

  /**
   * Get all registered message types
   */
  getRegisteredTypes(): string[] {
    return Array.from(this.registry.keys());
  }
}

// Export singleton instance
export const MessageRegistry = new MessageRegistryClass();
