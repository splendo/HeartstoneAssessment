# Uncomment the next line to define a global platform for your project
# platform :ios, '9.0'

target 'HeartstoneCards' do
  # Comment the next line if you're not using Swift and don't want to use dynamic frameworks
  use_frameworks!

  pod 'Alamofire', '~> 4.7'
  pod 'SDWebImage', '~> 4.0'

  def testing_pods
    pod 'Quick'
    pod 'Nimble'
  end

  target 'HeartstoneCardsTests' do
    inherit! :search_paths
    testing_pods
  end

end
