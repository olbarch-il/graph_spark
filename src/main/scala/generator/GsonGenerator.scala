package generator

import com.github.javafaker.Faker
import generator.Model.edges.{HasMailbox, HasPersonalInformation, HasPhotosGallery, PhotoInGallery}
import generator.Model.nodes.{MailBox, PersonalInformation, Photo, PhotosGallery}

class GsonGenerator extends App {
  private val faker = new Faker()

  for (id <- 0 to 10) {
    // COMMON
    val userId = id.toString
    val screenName = faker.ancient().hero()
    val userUrl = faker.internet().url()
    val description = faker.lorem()

    // PERSONAL INFORMATION
    val personalCard = PersonalInformation(personalCardId = userId + 1,
      createdAt = faker.date().birthday(),
      firstName = faker.name().firstName(),
      lastName = faker.name().lastName(),
      birthDate = faker.date().birthday(),
      phone = faker.phoneNumber().phoneNumber(),
      email = faker.internet().emailAddress(),
      age = faker.number().numberBetween(18, 60),
      gender = getSex()
    )

    val hasPersonalInformation = HasPersonalInformation(from = userId, to = personalCard.personalCardId)


    // PHOTOS
    val photosGalleryId = (id + 10).toString
    val photosGallery = PhotosGallery(galleryId = photosGalleryId)
    val hasPhotosGallery = HasPhotosGallery(from = userId, to = photosGallery.galleryId)
    val photos = for (photoId <- List.range(0, 100)) yield {
      Photo(photoId = photoId.toString,
        photoName = faker.rockBand().name(),
        sizeInKb = faker.number().randomNumber(),
        weight = faker.number().randomNumber(),
        height = faker.number().randomDigit())
    }
    val photosInGallery: List[PhotoInGallery] = for(photo <- photos)
      yield PhotoInGallery(from = photo.photoId, to = photosGallery.galleryId)

    // POSTS
    val mailBox = MailBox(mailboxId = (id + 11).toString)
    val mailbox = HasMailbox(from = userId, to = mailBox.mailboxId)
    // todo all messages


  }

  def getSex(): String = {
    val ramdomN = faker.number.numberBetween(0, 1)
    if (ramdomN == 0) "Male" else "Female"
  }


}
