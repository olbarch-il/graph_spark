package generator

import com.github.javafaker.Faker
import com.google.gson.Gson
import generator.Model.edges._
import generator.Model.nodes._
import io.circe.generic.auto._
import io.circe.syntax._

import java.io.{BufferedWriter, FileWriter}
import java.time.LocalDate
import scala.collection.mutable.ListBuffer
import scala.util.Random

object GsonGenerator extends App {
  private val faker = new Faker()

  val nodes: ListBuffer[String] = new ListBuffer[String]()
  val edges: ListBuffer[String] = new ListBuffer[String]()


  val users = for (id <- 0 to 10) yield {
    import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
    val gson = new Gson()

    // COMMON
    val userId = id.toString
    val screenName = faker.ancient().hero()
    val userUrl = faker.internet().url()
    val description = faker.lorem().toString

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
    nodes += gson.toJson(personalCard)
    nodes += gson.toJson(HasPersonalInformation(from = userId, to = personalCard.personalCardId))

    // PHOTOS
    val photosGalleryId = (id + 10).toString
    val photosGallery = PhotosGallery(galleryId = photosGalleryId)
    nodes += photosGallery.asJson.noSpaces
    edges += HasPhotosGallery(from = userId, to = photosGallery.galleryId).asJson.noSpaces
    val photos = for (photoId <- List.range(0, 100)) yield {
      Photo(photoId = photoId.toString,
        photoName = faker.rockBand().name(),
        sizeInKb = faker.number().randomNumber(),
        weight = faker.number().randomNumber(),
        height = faker.number().randomDigit())
    }
    photos.foreach(nodes += _.asJson.noSpaces)
    for (photo <- photos) {
      edges += PhotoInGallery(from = photo.photoId, to = photosGallery.galleryId).asJson.noSpaces
    }


    // POSTS
    val mailBoxId = (id + 11).toString
    val mailBox = MailBox(mailboxId = mailBoxId)
    nodes += mailBox.asJson.noSpaces
    edges += HasMailBox(from = userId, to = mailBox.mailboxId).asJson.noSpaces


    // ADDRESS
    val addressId = (id + 12).toString
    val addressCard = AddressCard(addressId = addressId,
      country = faker.address().country(),
      state = faker.address().state(),
      city = faker.address().city(),
      street = faker.address().streetName()
    )
    nodes += addressCard.asJson.noSpaces
    edges += HasAddress(from = userId, to = addressId).asJson.noSpaces
    User(userId = userId,
      userUrl = userUrl,
      screenName = screenName,
      mailboxId = mailBoxId,
      description = description,
      personalCard = personalCard.personalCardId,
      friendsCard = "",
      partnerId = "",
      countersCard = ""
    )
  }


  def getRandomUserId(usersIds: List[String]): String = {
    Random.between(0, usersIds.size).toString
  }

  for (_ <- List.range(0, 1000)) {
    val message = Message(
      messageId = faker.idNumber().toString,
      text = faker.chuckNorris().fact(),
      createdDate = LocalDate.now().minusDays(Random.between(0, 10))
    )
    nodes += message.asJson.noSpaces
    val sender = getRandomUserId(users.map(_.userId).toList)
    val receiver = getRandomUserId(users.map(_.userId).toList)
    edges += MessageFromTo(from = sender, to = receiver).asJson.noSpaces
    edges += MessageInMailBox(from = sender, to = users(sender.toInt).mailboxId).asJson.noSpaces
    edges += MessageInMailBox(from = receiver, to = users(sender.toInt).mailboxId).asJson.noSpaces
  }

  users.map { user =>
    val card = FriendsCard(
      userId = user.userId,
      cardId = user.userId + 5,
      friendsIds = users.map(_.userId).toList
    )
    nodes += user.copy(friendsCard = card.cardId, partnerId = users.filter(_.userId != user.userId).head.userId).asJson.noSpaces
  }


  def getSex(): String = {
    val randomN = faker.number.numberBetween(0, 1)
    if (randomN == 0) "Male" else "Female"
  }

  val edgesTxt = "edges.txt"
  val nodesTxt = "nodes.txt"
  private val nodesJson: String = s"[${nodes.mkString(",")}]"
  private val edgesJson: String = s"[${edges.mkString(",")}]"
  private val edgesWriter = new BufferedWriter(new FileWriter(edgesTxt))
  private val nodesWriter = new BufferedWriter(new FileWriter(nodesTxt))
  edgesWriter.write(edgesJson)
  edgesWriter.close()
  nodesWriter.write(nodesJson)
  nodesWriter.close()

}
