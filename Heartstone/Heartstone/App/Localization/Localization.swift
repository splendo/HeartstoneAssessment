import Foundation

internal struct Localization {
    internal static var failedToLoadContentMessage: String {
        NSLocalizedString("message.failedToLoadContent",
                          tableName: "General",
                          value: "Failed to load content. Please try again later",
                          comment: "Message displayed to the user when some content failed to laod")
    }
}
